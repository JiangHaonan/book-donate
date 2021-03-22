package com.mutou.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mutou.book.mapper.BooksMapper;
import com.mutou.book.mapper.BooksMapperCustom;
import com.mutou.book.pojo.Books;
import com.mutou.book.pojo.vo.SearchBooksVO;
import com.mutou.book.service.BookService;
import com.mutou.book.service.TagService;
import com.mutou.enums.ItemType;
import com.mutou.order.pojo.Orders;
import com.mutou.order.service.OrderService;
import com.mutou.pojo.IMOOCJSONResult;
import com.mutou.pojo.PagedGridResult;
import com.mutou.pojo.bo.DonateItemsBO;
import com.mutou.pojo.bo.UniversalItem;
import com.mutou.service.BaseService;
import com.mutou.utils.RedisOperator;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class BookServiceImpl extends BaseService implements BookService {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private BooksMapper booksMapper;

    @Autowired
    private BooksMapperCustom booksMapperCustom;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TagService tagService;

    private ThreadPoolExecutor executor;

    @PostConstruct
    private void initExecutor() {
        // 定义线程名称
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("do-book-cache-factory-%d").build();
        // 一个线程就可以了，多个线程并发执行插入Redis缓存没有意义
        executor = new ThreadPoolExecutor(1, 1,
                60 * 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque(1024), threadFactory);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Books queryBookById(String bookId) {
        return booksMapper.selectByPrimaryKey(bookId);
    }

    /**
     * TODO 方法中很多魔法数字，可优化
     *
     * @param tag
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchBooksByTag(String tag,
                                            String sort,
                                            Integer page,
                                            Integer pageSize) {
        // 这里需要校验一下tag是否存在，否在redis中会存在很多不存在的tag
        if (tagService.getByTag(tag) == null) {
            return emptyData(page);
        }

        // 目前是2W本书的数据量，参照豆瓣网，没有联合Tag的查询，也就是Tag只能传一个
        // 这里针对每个Tag保存两份redis数据(两种排序)
        // 一开始想用redis的List去存储，经实验可能会存在并发问题，push重复的数据
        // 改用SortedSet来做存储
        String tagKey = (sort != null && sort.equals("c")) ? tag + "_c" : tag;
        String lengthKey = tagKey + "_length";

        Object length = redisOperator.get(lengthKey);

        // 当缓存不存在的时候
        if (length == null) {

            // 这里先返回数据给用户，然后去异步的把数据放入缓存，否则前端体验不好
            Map<String, Object> map = new HashMap<>();
            map.put("tag", tag);
            map.put("sort", sort);

            PageHelper.startPage(page, pageSize);
            List<SearchBooksVO> books = booksMapperCustom.queryBooksByTag(map);

            PagedGridResult pagedGridResult = setterPagedGrid(books, page);

            // 构造异步缓存任务并丢入线程池
            doAsyncCacheJob(() -> {

                if (redisOperator.containsKey(lengthKey)) {
                    return;
                }

                log.info("cache key:" + tagKey);

                // 需要去数据库查询了 数据量并不大的情况下，可以一次查出
                // 如果数据库比较大，我觉得分页的key需要加上page，缓存的是热点页的数据
                List<SearchBooksVO> allBooksVOS = booksMapperCustom.queryBooksByTag(map);

                if (allBooksVOS.size() > 0) {
                    Set<ZSetOperations.TypedTuple<Object>> typedTuples = new HashSet();
                    allBooksVOS.forEach(b -> {
                                ZSetOperations.TypedTuple<Object> tuple = new DefaultTypedTuple<Object>(b,
                                        Double.valueOf(b.getAverage()));
                                typedTuples.add(tuple);
                            }
                    );
                    redisOperator.zadd(tagKey, typedTuples, 60 * 6);
                }

                // 缓存一个长度值，避免缓存穿透，过期时间比数据稍短一些
                redisOperator.set(lengthKey, allBooksVOS.size(), 60 * 5);

            });

            return pagedGridResult;
        } else {
            // 返回缓存内容
            long startPos = ((page - 1) * pageSize);
            long endPos = startPos + pageSize - 1;

            // 这边会有并发的问题，可能获取到的数据和length已经不对应了，但没事，这种错误前端是可以接受的
            Set<SearchBooksVO> cachedBooks = redisOperator.zrevrange(tagKey, startPos, endPos);

            long records = (Integer) length;
            return setterPagedGrid(cachedBooks.stream().collect(Collectors.toList()),
                    page, (int) (records / pageSize + (records % pageSize > 0 ? 1 : 0)), records);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchBooks(String keywords,
                                       String sort,
                                       Integer page,
                                       Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchBooksVO> list = booksMapperCustom.queryBooks(map);

        return setterPagedGrid(list, page);
    }

    @GlobalTransactional
    @Override
    public IMOOCJSONResult donate(DonateItemsBO donateItemsBO) {

        // 更新库存
        for (UniversalItem universalItem : donateItemsBO.getItemList()) {
            String bookId = universalItem.getItemId();
            Integer buyCounts = universalItem.getBuyCount();
            Books book = queryBookById(bookId);
            if (book == null) {
                throw new RuntimeException("参数错误");
            }
            if (booksMapperCustom.decreaseBookStock(bookId, buyCounts) != 1) {
                throw new RuntimeException("书籍：" + book.getTitle() + "已经没有足够的库存");
            }

            universalItem.setPrice(buyCounts * book.getPriceDonate());
            universalItem.setItemType(ItemType.TYPE_BOOK.type);
            universalItem.setItemName(book.getTitle());
        }

        // 创建订单（分布式事务，我这边用阿里黑科技seata）
        Orders createdOrder = orderService.createOrder(donateItemsBO);

        if (Objects.isNull(createdOrder)) {
            throw new RuntimeException("订单创建失败");
        }

        return IMOOCJSONResult.ok();
    }

    private void doAsyncCacheJob(Runnable task) {
        executor.execute(task);
    }

    @PreDestroy
    private void executorShutdown() {
        executor.shutdown();
    }
}
