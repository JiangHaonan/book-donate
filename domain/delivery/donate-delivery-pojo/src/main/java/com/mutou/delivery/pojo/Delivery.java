package com.mutou.delivery.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Delivery {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 配送状态
     */
    private Integer status;

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
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取配送状态
     *
     * @return status - 配送状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置配送状态
     *
     * @param status 配送状态
     */
    public void setStatus(Integer status) {
        this.status = status;
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
}