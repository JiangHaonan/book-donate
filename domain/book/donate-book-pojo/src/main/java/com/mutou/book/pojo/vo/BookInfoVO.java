package com.mutou.book.pojo.vo;

import com.mutou.book.pojo.Books;

import java.util.List;

public class BookInfoVO {

    private Books book;

    private List<String> tagList;

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
