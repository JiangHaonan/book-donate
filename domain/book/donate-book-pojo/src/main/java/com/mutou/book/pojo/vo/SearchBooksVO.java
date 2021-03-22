package com.mutou.book.pojo.vo;

/**
 * 用于展示书籍列表
 */
public class SearchBooksVO {

    private String bookId;
    private String title;
    private String author;
    private String imageUrl;
    private String average;
    private int price;
    private int priceDonate;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceDonate() {
        return priceDonate;
    }

    public void setPriceDonate(int priceDonate) {
        this.priceDonate = priceDonate;
    }
}
