package com.mutou.order.service;

import com.mutou.order.pojo.OrderStatus;
import com.mutou.order.pojo.Orders;
import com.mutou.pojo.bo.DonateItemsBO;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 弱restful的api，不对用户暴露
 */
@FeignClient("donate-order-service")
@RequestMapping("order-api")
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param donateItemsBO
     * @return
     */
    @PostMapping("order/create")
    public Orders createOrder(@RequestBody DonateItemsBO donateItemsBO);

    /**
     * 用于更新订单相关信息
     * @param order
     */
    @PostMapping("order/update")
    public void updateOrder(@RequestBody Orders order);

    /**
     * 根据orderId查询订单
     * @param orderId
     * @return
     */
    @GetMapping("order/{orderId}")
    public Orders queryOrderInfo(@PathVariable("orderId") String orderId);

    /**
     * 通过orderId和userId查询
     * @param orderId
     * @param userId
     * @return
     */
    @GetMapping("order/{orderId}/user/{userId}")
    public Orders queryOrderByIdAndUserId(@PathVariable("orderId") String orderId,
                                          @PathVariable("userId") String userId);

    /**
     * 根据userId查询订单列表
     * @param userId
     * @return
     */
    @GetMapping("order/user/{userId}")
    public List<Orders> queryOrderByUserId(@PathVariable("userId") String userId);

    /**
     * 更新订单状态表信息
     * @param orderId
     * @param status
     * @param preStatus
     */
    @PostMapping("order/{orderId}/status/{status}/update")
    public void updateOrderStatus(@PathVariable("orderId") String orderId,
                                  @PathVariable("status") Integer status,
                                  @ApiParam("preStatus") Integer preStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    @GetMapping("orderStatus/{orderId}")
    public OrderStatus queryOrderStatusInfo(@PathVariable("orderId") String orderId);

    /**
     * 关闭超时未支付订单
     */
    @PostMapping("order/close")
    public void closeOrder();

}
