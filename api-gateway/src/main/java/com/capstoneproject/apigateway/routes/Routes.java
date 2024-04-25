package com.capstoneproject.apigateway.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return route("product-service")
                .route(RequestPredicates.path("/api/product/**"), http("http://localhost:50842"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> transactionServiceRoute() {
        return route("transaction-service")
                .route(RequestPredicates.path("/api/transaction/**"), http("http://localhost:8087"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> supplierServiceRoute() {
        return route("supplier-service")
                .route(RequestPredicates.path("/api/supplier/**"), http("http://localhost:8082"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return route("order-service")
                .route(RequestPredicates.path("/api/order/**"), http("http://localhost:8085"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> itemServiceRoute() {
        return route("item-service")
                .route(RequestPredicates.path("/api/item/**"), http("http://localhost:8083"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> categoryServiceRoute() {
        return route("category-service")
                .route(RequestPredicates.path("/api/category/**"), http("http://localhost:61308"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> brandServiceRoute() {
        return route("brand-service")
                .route(RequestPredicates.path("/api/brand/**"), http("http://localhost:61309"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return route("user-service")
                .route(RequestPredicates.path("/api/user/**"), http("http://localhost:1109"))
                .build();
    }
}
