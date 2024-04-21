package com.capstoneproject.orderservice.service;

import com.capstoneproject.orderservice.dto.OrderItemRequest;
import com.capstoneproject.orderservice.dto.OrderItemResponse;
import com.capstoneproject.orderservice.dto.OrderRequest;
import com.capstoneproject.orderservice.dto.OrderResponse;
import com.capstoneproject.orderservice.model.Order;
import com.capstoneproject.orderservice.model.OrderItem;
import com.capstoneproject.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;


    public void createOrder(OrderRequest orderRequest) {
        if(!orderRequest.getOrderItem().isEmpty() && orderRequest.getOrderItem() != null){
            Order order = Order.builder()
                    .userId(orderRequest.getUserId())
                    .type(orderRequest.getType())
                    .status(orderRequest.getStatus())
                    .subTotal(orderRequest.getSubTotal())
                    .itemDiscount(orderRequest.getItemDiscount())
                    .tax(orderRequest.getTax())
                    .shipping(orderRequest.getShipping())
                    .total(orderRequest.getTotal())
                    .promo(orderRequest.getPromo())
                    .discount(orderRequest.getDiscount())
                    .grandTotal(orderRequest.getGrandTotal())
                    .orderItem(orderRequest.getOrderItem().stream().map(this::mapToOrderItem).toList())
                    .build();
            order.setCreatedBy("ram"); //need to implement login and name should come from userid
            order.setCreatedAt(LocalDateTime.now());
            orderRepository.save(order);
            log.info("Order {} is saved", order.getId());
        }
        log.info("Order not saved.. PLease Place some order");

    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    public OrderResponse getOrderById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        return orderOpt.isPresent() ? orderOpt.map(this::mapToOrderResponse).get() : null;
    }

    public void updateOrder(Long id, OrderRequest orderRequest) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.map(opt ->
                    Order.builder()
                            .id(opt.getId())
                            .userId(Optional.ofNullable(orderRequest.getUserId()).orElse(opt.getUserId()))
                            .type(Optional.ofNullable(orderRequest.getType()).orElse(opt.getType()))
                            .status(Optional.ofNullable(orderRequest.getStatus()).orElse(opt.getStatus()))
                            .subTotal(Optional.ofNullable(orderRequest.getSubTotal()).orElse(opt.getSubTotal()))
                            .itemDiscount(Optional.ofNullable(orderRequest.getItemDiscount()).orElse(opt.getItemDiscount()))
                            .tax(Optional.ofNullable(orderRequest.getTax()).orElse(opt.getTax()))
                            .shipping(Optional.ofNullable(orderRequest.getShipping()).orElse(opt.getShipping()))
                            .total(Optional.ofNullable(orderRequest.getTotal()).orElse(opt.getTotal()))
                            .promo(Optional.ofNullable(orderRequest.getPromo()).orElse(opt.getPromo()))
                            .discount(Optional.ofNullable(orderRequest.getDiscount()).orElse(opt.getDiscount()))
                            .grandTotal(Optional.ofNullable(orderRequest.getGrandTotal()).orElse(opt.getGrandTotal()))
                            .orderItem(Optional.ofNullable(orderRequest.getOrderItem().stream().map(this::mapToOrderItem).toList()).orElse(opt.getOrderItem()))
                            .createdBy(opt.getCreatedBy())
                            .updatedBy("shyam") //change the name with the login credential
                            .createdAt(opt.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .build()
            ).get();
            orderRepository.saveAndFlush(order);
            log.info("Order {} is updated", order.getId());
        }
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .type(order.getType())
                .status(order.getStatus())
                .subTotal(order.getSubTotal())
                .itemDiscount(order.getItemDiscount())
                .tax(order.getTax())
                .shipping(order.getShipping())
                .total(order.getTotal())
                .promo(order.getPromo())
                .discount(order.getDiscount())
                .grandTotal(order.getGrandTotal())
                .orderItem(order.getOrderItem().stream().map(this::mapToOrderItemResponse).toList())
                .build();
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem){
        return  OrderItemResponse.builder()
                .id(orderItem.getId())
                .itemId(orderItem.getItemId())
                .sku(orderItem.getSku())
                .price(orderItem.getPrice())
                .discount(orderItem.getDiscount())
                .orderedQuantity(orderItem.getOrderedQuantity())
                .arrivedQuantity(orderItem.getArrivedQuantity())
                .content(orderItem.getContent())
                .build();
    }
    private OrderItem mapToOrderItem(OrderItemRequest orderItemRequest){
        return  OrderItem.builder()
                .itemId(orderItemRequest.getItemId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .discount(orderItemRequest.getDiscount())
                .orderedQuantity(orderItemRequest.getOrderedQuantity())
                .arrivedQuantity(orderItemRequest.getArrivedQuantity())
                .content(orderItemRequest.getContent())
                .build();
    }
}

