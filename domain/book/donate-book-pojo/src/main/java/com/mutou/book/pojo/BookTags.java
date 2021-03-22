package com.mutou.book.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "book_tags")
public class BookTags {
    /**
     * 书籍ID
     */
    @Column(name = "book_id")
    private String bookId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 获取书籍ID
     *
     * @return book_id - 书籍ID
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * 设置书籍ID
     *
     * @param bookId 书籍ID
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     * 获取标签
     *
     * @return tag - 标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
     *
     * @param tag 标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}