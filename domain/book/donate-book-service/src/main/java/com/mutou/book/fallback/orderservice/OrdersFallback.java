package com.mutou.book.fallback.orderservice;

import com.mutou.order.pojo.OrderStatus;
import com.mutou.order.pojo.Orders;
import com.mutou.pojo.bo.DonateItemsBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("fake-name")
@Slf4j
public class OrdersFallback implements OrdersFeignClient {

    @Override
    public Orders createOrder(DonateItemsBO donateItemsBO) {
        log.error("订单创建异常");
        return null;
    }

    @Override
    public void updateOrder(Orders order) {

    }

    @Override
    public Orders queryOrderInfo(String orderId) {
        return null;
    }

    @Override
    public Orders queryOrderByIdAndUserId(String orderId, String userId) {
        return null;
    }

    @Override
    public List<Orders> queryOrderByUserId(String userId) {
        return null;
    }

    @Override
    public void updateOrderStatus(String orderId, Integer status, Integer preStatus) {

    }

    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return null;
    }

    @Override
    public void closeOrder() {

    }
}
