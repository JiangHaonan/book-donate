package com.mutou.delivery.service;

import com.mutou.delivery.pojo.Delivery;
import com.mutou.order.pojo.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("donate-delivery-service")
@RequestMapping("delivery-api")
public interface DeliveryService {

    /**
     * 用于创建配送信息
     * @param orders
     * @return
     */
    @PostMapping("delivery/create")
    public Delivery createDelivery(@RequestBody Orders orders);

    /**
     * 通过用户Id查找Delivery列表
     * @param userId
     * @return
     */
    @GetMapping("delivery/user/{userId}")
    public List<Delivery> queryListByUserId(@PathVariable String userId);

    /**
     * 根据配送单号和用户ID查询Delivery
     * @param deliveryId
     * @param userId
     * @return
     */
    @GetMapping("delivery/{deliveryId}/user/{userId}")
    public Delivery queryByIdAndUserId(@PathVariable String deliveryId,
                                       @PathVariable String userId);

}
