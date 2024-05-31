package com.capstoneproject.orderservice.controller;

import com.capstoneproject.orderservice.dto.OrderRequest;
import com.capstoneproject.orderservice.dto.OrderResponse;
import com.capstoneproject.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/getBySupplier")
    @ResponseStatus(HttpStatus.OK)
    public  List<OrderResponse> getOrdersBySupplier(@RequestParam("userId") Long userId,@RequestParam("type") int type,@RequestParam("status") int status){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setType(type);
        orderRequest.setStatus(status);
        return orderService.getAllOrdersByUserIdAndType(orderRequest);
    }

    @GetMapping("/getAllByStatus")
    @ResponseStatus(HttpStatus.OK)
    public  List<OrderResponse> getAllByStatus(@RequestParam("status") int status){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setStatus(status);
        return orderService.getAllByStatus(orderRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        orderService.updateOrder(id,orderRequest);
    }

    // Get an order by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

}
