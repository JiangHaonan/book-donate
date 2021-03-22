package com.mutou.order.service.impl;

import com.mutou.area.pojo.Area;
import com.mutou.area.service.AreaService;
import com.mutou.enums.OrderStatusEnum;
import com.mutou.enums.YesOrNo;
import com.mutou.order.mapper.OrderItemsMapper;
import com.mutou.order.mapper.OrderStatusMapper;
import com.mutou.order.mapper.OrderStatusMapperCustom;
import com.mutou.order.mapper.OrdersMapper;
import com.mutou.order.pojo.OrderItems;
import com.mutou.order.pojo.OrderStatus;
import com.mutou.order.pojo.Orders;
import com.mutou.order.service.OrderService;
import com.mutou.pojo.bo.DonateItemsBO;
import com.mutou.pojo.bo.UniversalItem;
import com.mutou.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AreaService areaService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrderStatusMapperCustom orderStatusMapperCustom;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Orders createOrder(DonateItemsBO donateItemsBO) {

        String userId = donateItemsBO.getUserId();
        String areaId = donateItemsBO.getAreaId();
        String leftMsg = donateItemsBO.getLeftMsg();
        Integer payMethod = donateItemsBO.getPayMethod();
        List<UniversalItem> itemList = donateItemsBO.getItemList();

        String orderId = sid.nextShort();
        Area area = areaService.queryAreaById(areaId);

        Integer payAmount = 0;

        // 创建新订单
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setAreaId(areaId);
        newOrder.setUserId(userId);
        newOrder.setPayMethod(payMethod);

        newOrder.setRealPayAmount(payAmount);
        newOrder.setTotalAmount(payAmount);

        newOrder.setReceiveName(area.getReceiver());
        newOrder.setReceiveMobile(area.getMobile());
        newOrder.setReceiveAddress(area.getProvince() + " "
                + area.getCity() + " "
                + area.getDistrict() + " "
                + area.getDetail());
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreateTime(new Date());
        newOrder.setUpdateTime(new Date());

        // 循环保存子订单
        for (UniversalItem item : itemList) {
            String orderItemId = sid.nextShort();

            OrderItems orderItem = new OrderItems();
            orderItem.setBuyCounts(item.getBuyCount());
            orderItem.setId(orderItemId);
            orderItem.setItemName(item.getItemName());
            orderItem.setItemType(item.getItemType());
            orderItem.setItemId(item.getItemId());
            orderItem.setPrice(item.getPrice());
            orderItem.setOrderId(orderId);
            orderItemsMapper.insert(orderItem);

            payAmount += item.getPrice() * item.getBuyCount();
        }

        newOrder.setTotalAmount(payAmount);
        newOrder.setRealPayAmount(payAmount);
        ordersMapper.insert(newOrder);

        // 创建订单状态 设置为未支付状态
        OrderStatus waitDeliveryOrderStatus = new OrderStatus();
        waitDeliveryOrderStatus.setOrderId(orderId);
        waitDeliveryOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitDeliveryOrderStatus.setCreateTime(new Date());
        orderStatusMapper.insert(waitDeliveryOrderStatus);

        // 返回订单信息
        return newOrder;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrder(Orders order) {
        ordersMapper.updateByPrimaryKeySelective(order);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryOrderInfo(String orderId) {
        return ordersMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryOrderByIdAndUserId(String orderId, String userId) {
        Example orderExample = new Example(Orders.class);
        Example.Criteria orderCriteria = orderExample.createCriteria();

        orderCriteria.andEqualTo("id", orderId)
                .andEqualTo("userId", userId);

        return ordersMapper.selectOneByExample(orderExample);
    }

    @Override
    public List<Orders> queryOrderByUserId(String userId) {
        Example orderExample = new Example(Orders.class);
        Example.Criteria orderCriteria = orderExample.createCriteria();
        orderCriteria.andEqualTo("userId", userId);

        return ordersMapper.selectByExample(orderExample);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer status, Integer preStatus) {

        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
        if (orderStatus == null) {
            throw new RuntimeException("订单号不存在");
        }

        if (preStatus != null && preStatus != orderStatus.getOrderStatus()) {
            throw new RuntimeException("订单状态已发生变化，请刷新后重试");
        }

        if (orderStatusMapperCustom.updateStatusByOrderId(orderId, status, preStatus) != 1) {
            throw new RuntimeException("更新订单状态失败，订单状态可能已发生变化，请刷新后重试");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {

        // 查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        for (OrderStatus os : list) {
            // 获得订单创建时间
            Date createdTime = os.getCreateTime();
            // 和当前时间进行对比
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days >= 1) {
                // 超过1天，关闭订单
                doCloseOrder(os.getOrderId(), OrderStatusEnum.WAIT_PAY.type);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId, Integer preStatus) {
        updateOrderStatus(orderId, OrderStatusEnum.CLOSE.type, preStatus);
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
