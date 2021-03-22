package com.mutou.enums;

/**
 * @Description: 配送状态
 */
public enum DeliveryStatusEnum {

    // 对应ORDER的WAIT_RECEIVE状态
    DELIVERED(10, "已发货"),
    // TODO 中间状态等待添加
    SIGNED(100, "已签收");

    public final Integer type;
    public final String value;

    DeliveryStatusEnum(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}
