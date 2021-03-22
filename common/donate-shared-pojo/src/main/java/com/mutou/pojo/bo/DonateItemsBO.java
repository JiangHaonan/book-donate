package com.mutou.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 捐赠项目信息
 */
@Data
@ApiModel("订单提交的商品详情")
public class DonateItemsBO {

    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    @ApiModelProperty(value = "捐赠区域ID", required = true)
    private String areaId;

    @ApiModelProperty(value = "支付方式(1:微信 2:支付宝)", required = true)
    private Integer payMethod;

    @ApiModelProperty(value = "商品列表", required = true)
    private List<UniversalItem> itemList;

    @ApiModelProperty(value = "留言", required = false)
    private String leftMsg;

}
