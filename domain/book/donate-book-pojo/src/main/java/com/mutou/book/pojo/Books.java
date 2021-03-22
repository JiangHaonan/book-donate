package com.mutou.book.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Books {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * ISBN
     */
    private String isbn;

    /**
     * 书名
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 标签
     */
    private String tag;

    /**
     * 评分人数
     */
    @Column(name = "num_raters")
    private Integer numRaters;

    /**
     * 评分
     */
    private String average;

    /**
     * 精简装
     */
    private String binding;

    /**
     * 页数
     */
    private String pages;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 豆瓣链接
     */
    private String url;

    /**
     * 售价
     */
    private Integer price;

    /**
     * 捐赠价
     */
    @Column(name = "price_donate")
    private Integer priceDonate;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 扩展字段
     */
    private String extend;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 作者简介
     */
    @Column(name = "author_intro")
    private String authorIntro;

    /**
     * 图书原名 Url
     */
    @Column(name = "origin_title")
    private String originTitle;

    /**
     * 内容摘要
     */
    @Column(name = "content_summary")
    private String contentSummary;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取ISBN
     *
     * @return isbn - ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * 设置ISBN
     *
     * @param isbn ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * 获取书名
     *
     * @return title - 书名
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置书名
     *
     * @param title 书名
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
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

    /**
     * 获取评分人数
     *
     * @return num_raters - 评分人数
     */
    public Integer getNumRaters() {
        return numRaters;
    }

    /**
     * 设置评分人数
     *
     * @param numRaters 评分人数
     */
    public void setNumRaters(Integer numRaters) {
        this.numRaters = numRaters;
    }

    /**
     * 获取评分
     *
     * @return average - 评分
     */
    public String getAverage() {
        return average;
    }

    /**
     * 设置评分
     *
     * @param average 评分
     */
    public void setAverage(String average) {
        this.average = average;
    }

    /**
     * 获取精简装
     *
     * @return binding - 精简装
     */
    public String getBinding() {
        return binding;
    }

    /**
     * 设置精简装
     *
     * @param binding 精简装
     */
    public void setBinding(String binding) {
        this.binding = binding;
    }

    /**
     * 获取页数
     *
     * @return pages - 页数
     */
    public String getPages() {
        return pages;
    }

    /**
     * 设置页数
     *
     * @param pages 页数
     */
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * 获取出版社
     *
     * @return publisher - 出版社
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 设置出版社
     *
     * @param publisher 出版社
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 获取豆瓣链接
     *
     * @return url - 豆瓣链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置豆瓣链接
     *
     * @param url 豆瓣链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取售价
     *
     * @return price - 售价
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置售价
     *
     * @param price 售价
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取捐赠价
     *
     * @return price_donate - 捐赠价
     */
    public Integer getPriceDonate() {
        return priceDonate;
    }

    /**
     * 设置捐赠价
     *
     * @param priceDonate 捐赠价
     */
    public void setPriceDonate(Integer priceDonate) {
        this.priceDonate = priceDonate;
    }

    /**
     * 获取库存
     *
     * @return stock - 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     *
     * @param stock 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取图片
     *
     * @return image_url - 图片
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片
     *
     * @param imageUrl 图片
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取扩展字段
     *
     * @return extend - 扩展字段
     */
    public String getExtend() {
        return extend;
    }

    /**
     * 设置扩展字段
     *
     * @param extend 扩展字段
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取作者简介
     *
     * @return author_intro - 作者简介
     */
    public String getAuthorIntro() {
        return authorIntro;
    }

    /**
     * 设置作者简介
     *
     * @param authorIntro 作者简介
     */
    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    /**
     * 获取图书原名 Url
     *
     * @return origin_title - 图书原名 Url
     */
    public String getOriginTitle() {
        return originTitle;
    }

    /**
     * 设置图书原名 Url
     *
     * @param originTitle 图书原名 Url
     */
    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    /**
     * 获取内容摘要
     *
     * @return content_summary - 内容摘要
     */
    public String getContentSummary() {
        return contentSummary;
    }

    /**
     * 设置内容摘要
     *
     * @param contentSummary 内容摘要
     */
    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary;
    }
}