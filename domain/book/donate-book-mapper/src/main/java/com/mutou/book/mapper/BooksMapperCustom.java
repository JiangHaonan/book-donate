package com.mutou.book.mapper;

import com.mutou.book.pojo.vo.SearchBooksVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BooksMapperCustom {

    public List<SearchBooksVO> queryBooksByTag(@Param("paramsMap") Map<String, Object> map);

    public List<SearchBooksVO> queryBooks(@Param("paramsMap") Map<String, Object> map);

    public int decreaseBookStock(@Param("bookId")String bookId, @Param("pendingCounts")Integer pendingCounts);
}
