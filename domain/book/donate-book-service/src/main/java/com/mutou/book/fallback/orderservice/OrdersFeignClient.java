package com.mutou.book.fallback.orderservice;

import com.mutou.order.service.OrderService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "donate-order-service", fallback = OrdersFallback.class)
public interface OrdersFeignClient extends OrderService {

}
