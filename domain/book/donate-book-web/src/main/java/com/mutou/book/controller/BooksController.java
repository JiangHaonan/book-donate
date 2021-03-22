package com.mutou.book.controller;

import com.mutou.book.pojo.Books;
import com.mutou.book.pojo.vo.BookInfoVO;
import com.mutou.book.service.BookService;
import com.mutou.book.service.TagService;
import com.mutou.controller.BaseController;
import com.mutou.enums.PayMethod;
import com.mutou.pojo.IMOOCJSONResult;
import com.mutou.pojo.PagedGridResult;
import com.mutou.pojo.bo.DonateItemsBO;
import com.mutou.utils.StrUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Api(value = "书籍接口", tags = {"书籍信息展示的相关接口"})
@RestController
@RequestMapping("books")
public class BooksController extends BaseController {

    @Autowired
    private BookService bookService;

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "查询书籍详情", notes = "查询书籍详情", httpMethod = "GET")
    @GetMapping("/info/{bookId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "bookId", value = "书籍id", required = true)
            @PathVariable String bookId) {

        if (StringUtils.isBlank(bookId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        Books book = bookService.queryBookById(bookId);
        if (book == null) {
            return IMOOCJSONResult.ok(null);
        }
        BookInfoVO bookInfoVO = new BookInfoVO();
        bookInfoVO.setBook(book);
        if (StringUtils.isNotBlank(book.getTag())) {
            bookInfoVO.setTagList(Arrays.asList(book.getTag().split(StrUtils.COMMA)));
        }

        return IMOOCJSONResult.ok(bookInfoVO);
    }

    @ApiOperation(value = "通过关键字(作者、ISBN、书名)搜索书籍列表",
                  notes = "通过关键字(作者、ISBN、书名)搜索书籍列表", httpMethod = "GET")
    @GetMapping("/info/search")
    public IMOOCJSONResult search(
            @ApiParam(name = "keywords", value = "关键字(作者、ISBN、书名)", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序(a:按平均分排序, 其他:按主题排序)", required = false)
            @RequestParam(required = false) String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = true)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = true)
            @RequestParam Integer pageSize) {

        // 关键字变换莫测，这里就不做缓存了
        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg("请输入关键字");
        }

        if (page == null || page <= 0) {
            page = 1;
        }

        if (pageSize == null || pageSize < 0) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = bookService.searchBooks(keywords,
                sort,
                page,
                pageSize);

        return IMOOCJSONResult.ok(grid);
    }


    @ApiOperation(value = "查询书籍标签列表", notes = "查询书籍标签列表", httpMethod = "GET")
    @GetMapping("/tags")
    public IMOOCJSONResult tags() {
        return IMOOCJSONResult.ok(tagService.getTagList());
    }


    @ApiOperation(value = "通过标签搜索书籍列表", notes = "通过标签搜索书籍列表", httpMethod = "GET")
    @GetMapping("/info/tag/{tag}/search")
    public IMOOCJSONResult searchByKeywords(
            @ApiParam(name = "tag", value = "标签", required = true)
            @PathVariable String tag,
            @ApiParam(name = "sort", value = "排序(a:按平均分排序, 其他:按主题排序)", required = false)
            @RequestParam(required = false) String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = true)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = true)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(tag)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null || page <= 0) {
            page = 1;
        }

        if (pageSize == null || pageSize < 0) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = bookService.searchBooksByTag(tag,
                sort,
                page,
                pageSize);

        return IMOOCJSONResult.ok(grid);
    }

    @ApiOperation(value = "捐赠书籍", notes = "捐赠书籍", httpMethod = "POST")
    @PostMapping("/donate")
    public IMOOCJSONResult donate(@RequestBody DonateItemsBO donateItemsBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        if (!checkUserIllegalOperation(request, donateItemsBO.getUserId())) {
            return IMOOCJSONResult.errorMsg("非法操作！");
        }
        if (donateItemsBO.getPayMethod() != PayMethod.WEIXIN.type
                && donateItemsBO.getPayMethod() != PayMethod.ALIPAY.type ) {
            return IMOOCJSONResult.errorMsg("支付方式不支持！");
        }

        if (CollectionUtils.isEmpty(donateItemsBO.getItemList())) {
            return IMOOCJSONResult.errorMsg("捐赠项目数据错误！");
        }

        if (StringUtils.isBlank(donateItemsBO.getAreaId())) {
            return IMOOCJSONResult.errorMsg("请选择捐赠地区！");
        }

        return bookService.donate(donateItemsBO);
    }

}
