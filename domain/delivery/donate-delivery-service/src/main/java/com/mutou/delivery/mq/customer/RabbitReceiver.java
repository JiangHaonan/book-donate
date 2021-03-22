package com.mutou.delivery.mq.customer;

import com.mutou.delivery.service.DeliveryService;
import com.mutou.order.pojo.Orders;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitReceiver {

    @Autowired
    private DeliveryService orderDeliveryService;

    /**
     * 组合使用监听
     *
     * @param order
     * @param headers
     * @throws Exception
     * @RabbitListener @QueueBinding @Queue @Exchange
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.delivery.exchange.name}",
                    durable = "${spring.rabbitmq.listener.delivery.exchange.durable}"),
            exchange = @Exchange(name = "${spring.rabbitmq.listener.delivery.exchange.name}",
                    durable = "${spring.rabbitmq.listener.delivery.exchange.durable}",
                    type = "${spring.rabbitmq.listener.delivery.exchange.type}"),
//                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.delivery.exchange" +
//                            ".ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.delivery.exchange.key}"
    )
    )
    @RabbitHandler
    public void onMessage(@Payload Orders order, @Headers Map<String,Object> headers) throws Exception {
        //	1. 收到消息以后进行业务端消费处理
        //  2. 这边使用自动ACK了，如果说方法执行流程中抛出了异常，那么order服务在一段时间后发现状态没有更新，仍然会继续投递
        orderDeliveryService.createDelivery(order);
    }


}
