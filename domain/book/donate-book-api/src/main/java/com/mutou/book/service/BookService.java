package com.mutou.book.service;

import com.mutou.book.pojo.Books;
import com.mutou.pojo.IMOOCJSONResult;
import com.mutou.pojo.PagedGridResult;
import com.mutou.pojo.bo.DonateItemsBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("donate-book-service")
@RequestMapping("book-api")
public interface BookService {

    /**
     * 根据书籍ID查询详情
     * @param bookId
     * @return
     */
    @GetMapping("info")
    public Books queryBookById(@RequestParam("bookId") String bookId);

    /**
     * 通过标签查询书籍列表
     * @param tag
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("tag/pagedBooks")
    public PagedGridResult searchBooksByTag(@RequestParam String tag,
                                            @RequestParam String sort,
                                            @RequestParam Integer page,
                                            @RequestParam Integer pageSize);

    /**
     * 通过关键字查询书籍列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("pageBooks")
    public PagedGridResult searchBooks(@RequestParam String keywords,
                                       @RequestParam String sort,
                                       @RequestParam Integer page,
                                       @RequestParam Integer pageSize);

    /**
     * 捐赠书籍
     * @param donateItemsBO
     * @return
     */
    @PostMapping("donate")
    public IMOOCJSONResult donate(@RequestBody DonateItemsBO donateItemsBO);

}
