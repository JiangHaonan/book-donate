package com.mutou.order.mapper;

import org.apache.ibatis.annotations.Param;

public interface OrderStatusMapperCustom {

    public int updateStatusByOrderId(@Param("orderId") String orderId,
                                      @Param("status") Integer status,
                                      @Param("preStatus") Integer preStatus);
}
