package com.mutou.order.pojo.VO;

import com.mutou.order.pojo.OrderStatus;
import com.mutou.order.pojo.Orders;

public class OrderVO {

    private Orders orders;

    private OrderStatus orderStatus;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
