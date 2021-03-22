package com.mutou.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Orders {
    /**
     * 主键订单号
     */
    @Id
    private String id;

    /**
     * 订单配送id
     */
    @Column(name = "delivery_id")
    private String deliveryId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 收货人快照
     */
    @Column(name = "receive_name")
    private String receiveName;

    /**
     * 收货人手机号快照
     */
    @Column(name = "receive_mobile")
    private String receiveMobile;

    /**
     * 收获地址快照
     */
    @Column(name = "receive_address")
    private String receiveAddress;

    /**
     * 地区表ID
     */
    @Column(name = "area_id")
    private String areaId;

    /**
     * 订单总价格
     */
    @Column(name = "total_amount")
    private Integer totalAmount;

    /**
     * 实际支付价格
     */
    @Column(name = "real_pay_amount")
    private Integer realPayAmount;

    /**
     * 支付方式
     */
    @Column(name = "pay_method")
    private Integer payMethod;

    /**
     * 捐助留言
     */
    @Column(name = "left_msg")
    private String leftMsg;

    /**
     * 扩展字段
     */
    private String extend;

    /**
     * 是否逻辑删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 创建时间（成交时间）
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取主键订单号
     *
     * @return id - 主键订单号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键订单号
     *
     * @param id 主键订单号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取订单配送id
     *
     * @return delivery_id - 订单配送id
     */
    public String getDeliveryId() {
        return deliveryId;
    }

    /**
     * 设置订单配送id
     *
     * @param deliveryId 订单配送id
     */
    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取收货人快照
     *
     * @return receive_name - 收货人快照
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**
     * 设置收货人快照
     *
     * @param receiveName 收货人快照
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * 获取收货人手机号快照
     *
     * @return receive_mobile - 收货人手机号快照
     */
    public String getReceiveMobile() {
        return receiveMobile;
    }

    /**
     * 设置收货人手机号快照
     *
     * @param receiveMobile 收货人手机号快照
     */
    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    /**
     * 获取收获地址快照
     *
     * @return receive_address - 收获地址快照
     */
    public String getReceiveAddress() {
        return receiveAddress;
    }

    /**
     * 设置收获地址快照
     *
     * @param receiveAddress 收获地址快照
     */
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    /**
     * 获取地区表ID
     *
     * @return area_id - 地区表ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 设置地区表ID
     *
     * @param areaId 地区表ID
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取订单总价格
     *
     * @return total_amount - 订单总价格
     */
    public Integer getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置订单总价格
     *
     * @param totalAmount 订单总价格
     */
    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取实际支付价格
     *
     * @return real_pay_amount - 实际支付价格
     */
    public Integer getRealPayAmount() {
        return realPayAmount;
    }

    /**
     * 设置实际支付价格
     *
     * @param realPayAmount 实际支付价格
     */
    public void setRealPayAmount(Integer realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    /**
     * 获取支付方式
     *
     * @return pay_method - 支付方式
     */
    public Integer getPayMethod() {
        return payMethod;
    }

    /**
     * 设置支付方式
     *
     * @param payMethod 支付方式
     */
    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    /**
     * 获取捐助留言
     *
     * @return left_msg - 捐助留言
     */
    public String getLeftMsg() {
        return leftMsg;
    }

    /**
     * 设置捐助留言
     *
     * @param leftMsg 捐助留言
     */
    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
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
     * 获取是否逻辑删除
     *
     * @return is_delete - 是否逻辑删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否逻辑删除
     *
     * @param isDelete 是否逻辑删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取创建时间（成交时间）
     *
     * @return create_time - 创建时间（成交时间）
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间（成交时间）
     *
     * @param createTime 创建时间（成交时间）
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