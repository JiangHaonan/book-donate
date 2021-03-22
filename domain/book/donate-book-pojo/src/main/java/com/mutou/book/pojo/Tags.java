package com.mutou.book.pojo;

import javax.persistence.Column;
import java.util.Date;

public class Tags {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 豆瓣URL
     */
    private String url;

    /**
     * 标签名称
     */
    private String tag;

    /**
     * 分类类型
     */
    private String type;

    /**
     * 项目分类（1.书籍，10.其他）
     */
    @Column(name = "item_type")
    private Integer itemType;

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
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取豆瓣URL
     *
     * @return url - 豆瓣URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置豆瓣URL
     *
     * @param url 豆瓣URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取标签名称
     *
     * @return tag - 标签名称
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签名称
     *
     * @param tag 标签名称
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取分类类型
     *
     * @return type - 分类类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置分类类型
     *
     * @param type 分类类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取项目分类（1.书籍，10.其他）
     *
     * @return item_type - 项目分类（1.书籍，10.其他）
     */
    public Integer getItemType() {
        return itemType;
    }

    /**
     * 设置项目分类（1.书籍，10.其他）
     *
     * @param itemType 项目分类（1.书籍，10.其他）
     */
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
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
}