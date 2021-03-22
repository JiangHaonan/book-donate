package com.mutou.book;

import com.mutou.book.fallback.orderservice.OrdersFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.mutou.book.mapper")
@ComponentScan(basePackages = {"com.mutou", "org.n3r.idworker"})
@EnableDiscoveryClient
@EnableCircuitBreaker
// 自己模块中的feignClient不要扫进去
// @EnableFeignClients(basePackages = {"com.mutou.order"})
@EnableFeignClients(
        clients = {
                OrdersFeignClient.class
        }
)
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
