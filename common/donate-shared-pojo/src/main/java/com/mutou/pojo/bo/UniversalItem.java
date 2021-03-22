package com.mutou.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用的商品信息
 */
@Data
@ApiModel("商品购买详情")
public class UniversalItem {

    @ApiModelProperty(value = "商品ID", required = true)
    private String itemId;

    @ApiModelProperty(value = "商品名称", required = false)
    private String itemName;

    @ApiModelProperty(value = "商品类型", required = false)
    private Integer itemType;

    @ApiModelProperty(value = "商品价格", required = false)
    private Integer price;

    @ApiModelProperty(value = "购买数量", required = true)
    private Integer buyCount;

}
