package com.mutou.delivery.service.impl;

import com.mutou.delivery.mapper.DeliveryMapper;
import com.mutou.delivery.pojo.Delivery;
import com.mutou.delivery.service.DeliveryService;
import com.mutou.enums.DeliveryStatusEnum;
import com.mutou.enums.OrderStatusEnum;
import com.mutou.order.pojo.Orders;
import com.mutou.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Sid sid;

    @GlobalTransactional
    @Override
    public Delivery createDelivery(Orders order) {

        order = orderService.queryOrderInfo(order.getId());

        if (!Objects.isNull(order.getDeliveryId())) {
            log.info("订单:{} 已经处理", order.getId());
            return null;
        }

        // 创建订单配送实体，设置状态为已下单
        Delivery delivery = new Delivery();
        delivery.setId(sid.nextShort());
        delivery.setOrderId(order.getId());
        delivery.setUserId(order.getUserId());
        delivery.setStatus(DeliveryStatusEnum.DELIVERED.type);
        delivery.setCreateTime(new Date());
        delivery.setUpdateTime(new Date());

        deliveryMapper.insert(delivery);

        // 设置物流ID
        order.setDeliveryId(delivery.getId());
        orderService.updateOrder(order);

        // 更新订单状态
        orderService.updateOrderStatus(order.getId(), OrderStatusEnum.WAIT_RECEIVE.type,
                OrderStatusEnum.WAIT_DELIVER.type);

        return delivery;
    }

    @Override
    public List<Delivery> queryListByUserId(String userId) {
        Example deliveryExample = new Example(Delivery.class);
        Example.Criteria deliveryCriteria = deliveryExample.createCriteria();
        deliveryCriteria.andEqualTo("userId", userId);

        return deliveryMapper.selectByExample(deliveryExample);
    }

    @Override
    public Delivery queryByIdAndUserId(String deliveryId, String userId) {
        Example deliveryExample = new Example(Delivery.class);
        Example.Criteria deliveryCriteria = deliveryExample.createCriteria();

        deliveryCriteria.andEqualTo("id", deliveryId)
                .andEqualTo("userId", userId);

        return deliveryMapper.selectOneByExample(deliveryExample);
    }
}
