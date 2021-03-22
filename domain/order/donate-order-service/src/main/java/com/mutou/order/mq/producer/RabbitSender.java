package com.mutou.order.mq.producer;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.delivery.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.delivery.exchange.key}")
    private String routingKey;

    public <T> void send(T message) {
        send(this.routingKey, message, null);
    }

    /**
     * 对外发送消息的方法
     *
     * @param message    具体的消息内容
     * @param properties 额外的附加属性
     * @throws Exception
     */
    public <T> void send(String routingKey, T message, Map<String, Object> properties) {

//        MessageHeaders mhs = new MessageHeaders(properties);
//        Message<?> msg = MessageBuilder.createMessage(message, mhs);

        // 	指定业务唯一的iD 在发布方确认的回调方法里，会带有这个参数。我们可以根据它很直观的看到哪条消息发送成功或失败。
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        MessagePostProcessor mpp = (amqpMessage) -> {
            MessageProperties mp = amqpMessage.getMessageProperties();
            mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            return amqpMessage;
        };

        rabbitTemplate.convertAndSend(exchange, routingKey, message, mpp, correlationData);
    }

}
