package com.mutou.order.controller;

import com.mutou.controller.BaseController;
import com.mutou.enums.OrderStatusEnum;
import com.mutou.order.mq.producer.RabbitSender;
import com.mutou.order.pojo.OrderStatus;
import com.mutou.order.pojo.Orders;
import com.mutou.order.pojo.VO.OrderVO;
import com.mutou.order.service.OrderService;
import com.mutou.pojo.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
@Slf4j
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitSender rabbitSender;

    @ApiOperation(value = "查询订单详情", notes = "查询订单详情", httpMethod = "GET")
    @GetMapping("info/{orderId}")
    public IMOOCJSONResult info(@ApiParam(name = "orderId", value = "订单id", required = true)
                                @PathVariable String orderId,
                                HttpServletRequest request) {
        // 通过网关层的用户校验才能进入到该方法，所以我们不要再次校验
        String userId = request.getHeader(UID_HEADER);
        if (userId == null) {
            return IMOOCJSONResult.ok(null);
        }
        Orders order = orderService.queryOrderByIdAndUserId(orderId, userId);
        if (order != null) {
            OrderVO orderVO = new OrderVO();
            OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
            orderVO.setOrders(order);
            orderVO.setOrderStatus(orderStatus);
            return IMOOCJSONResult.ok(orderVO);
        }
        return IMOOCJSONResult.ok(null);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "GET")
    @GetMapping("list")
    public IMOOCJSONResult list(HttpServletRequest request) {
        // request.getHeader(UID_HEADER) 同样是通过网关层进来的，安全有保证
        List<Orders> orders = orderService.queryOrderByUserId(request.getHeader(UID_HEADER));
        return IMOOCJSONResult.ok(orders);
    }

    @ApiOperation(value = "支付回调接口", notes = "支付回调接口(默认是不对用户开放的)", httpMethod = "POST")
    @PostMapping("notifyPaid")
    public Integer notifyPaid(String orderId) {

        // TODO 实际环境这里要对orderId还有其他的入参进行安全性校验

        orderService.updateOrderStatus(orderId,
                OrderStatusEnum.WAIT_DELIVER.type,
                OrderStatusEnum.WAIT_PAY.type);

        // 这里向配送系统发起通知，告诉它可以着手配送了，再通过Job保证可靠性投递
        rabbitSender.send(orderService.queryOrderInfo(orderId));

        // 返回比较通用的Integer值，方便其他系统调用
        return HttpStatus.OK.value();
    }
}
