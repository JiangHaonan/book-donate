package com.mutou.area.pojo;

import javax.persistence.Column;
import javax.persistence.Id;

public class Area {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 接收人手机号
     */
    private String mobile;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地区
     */
    private String district;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 扩展字段
     */
    private String extend;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

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
     * 获取接收人
     *
     * @return receiver - 接收人
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * 设置接收人
     *
     * @param receiver 接收人
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 获取接收人手机号
     *
     * @return mobile - 接收人手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置接收人手机号
     *
     * @param mobile 接收人手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取地区
     *
     * @return district - 地区
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置地区
     *
     * @param district 地区
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取详细地址
     *
     * @return detail - 详细地址
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置详细地址
     *
     * @param detail 详细地址
     */
    public void setDetail(String detail) {
        this.detail = detail;
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
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除
     *
     * @param isDelete 是否删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}