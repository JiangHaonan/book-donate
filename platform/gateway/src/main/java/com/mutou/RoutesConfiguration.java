package com.mutou;

import com.mutou.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                // user模块(登录、注册不需要验证)
                .route(r -> r.path("/passport/logout")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://DONATE-USER-SERVICE")
                )
                .route(r -> r.path("/passport/**")
                        .uri("lb://DONATE-USER-SERVICE")
                )
                // auth模块 暂时用不到对外开放
//                .route(r -> r.path("/auth-service/refresh")
//                        .filters(f -> f.filter(authFilter))
//                        .uri("lb://DONATE-AUTH-SERVICE")
//                )
                // area模块
                .route(r -> r.path("/area/**")
                        .uri("lb://DONATE-AREA-SERVICE")
                )
                // book模块
                .route(r -> r.path("/books/donate")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://DONATE-BOOK-SERVICE")
                )
                .route(r -> r.path("/books/**")
                        .uri("lb://DONATE-BOOK-SERVICE")
                )
                // order模块
                .route(r -> r.path("/orders/notifyPaid")
                        .uri("lb://DONATE-ORDER-SERVICE")
                )
                .route(r -> r.path("/orders/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://DONATE-ORDER-SERVICE")
                )
                // delivery模块
                .route(r -> r.path("/delivery/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://DONATE-DELIVERY-SERVICE")
                )
                // 后面都是swagger的
                .route(r -> r.path("/donate-user-service/v2/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://DONATE-USER-SERVICE")
                )
//                .route(r -> r.path("/donate-auth-service/v2/api-docs")
//                        .filters(f -> f.stripPrefix(1))
//                        .uri("lb://DONATE-AUTH-SERVICE")
//                )
                .route(r -> r.path("/donate-order-service/v2/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://DONATE-ORDER-SERVICE")
                )
                .route(r -> r.path("/donate-area-service/v2/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://DONATE-AREA-SERVICE")
                )
                .route(r -> r.path("/donate-delivery-service/v2/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://DONATE-DELIVERY-SERVICE")
                )
                .route(r -> r.path("/donate-book-service/v2/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://DONATE-BOOK-SERVICE")
                )
                .build();

    }

}
