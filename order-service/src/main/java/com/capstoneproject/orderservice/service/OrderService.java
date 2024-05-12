package com.capstoneproject.orderservice.service;

import com.capstoneproject.orderservice.client.InventoryClient;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void createOrder(OrderRequest orderRequest) {
        if (!orderRequest.getOrderItem().isEmpty()) {
            boolean inStock = true; // Check if all items are in stock

            if (inStock) {
                // Create the Order object
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
                        .build();

                order.setCreatedBy("System"); // Set the creator (need to implement login)
                order.setCreatedAt(LocalDateTime.now());

                List<OrderItem> orderItemList = orderRequest.getOrderItem().stream()
                        .map(item -> mapToOrderItem(item, order))
                        .toList();

                // Set the OrderItem list to the Order object
                order.setOrderItem(orderItemList);

                // Save the Order object
                orderRepository.save(order);
                log.info("Order {} is saved", order.getId());
            } else {
                throw new RuntimeException("Product not in stock");
            }
        } else {
            log.info("Order not saved. Please place some order.");
        }
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    public OrderResponse getOrderById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        return orderOpt.isPresent() ? orderOpt.map(this::mapToOrderResponse).get() : null;
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> orderItemResponses = order.getOrderItem() != null ?
                order.getOrderItem().stream().map(this::mapToOrderItemResponse).toList() :
                Collections.emptyList();

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
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .createdBy(order.getCreatedBy())
                .updatedBy(order.getUpdatedBy())
                .content(order.getContent())
                .orderItem(orderItemResponses)
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

    public OrderItem mapToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(orderItemRequest.getItemId());
        orderItem.setOrder(order);
        orderItem.setSku(orderItemRequest.getSku());
        orderItem.setPrice(orderItemRequest.getPrice());
        orderItem.setDiscount(orderItemRequest.getDiscount());
        orderItem.setOrderedQuantity(orderItemRequest.getOrderedQuantity());
        orderItem.setArrivedQuantity(orderItemRequest.getArrivedQuantity());
        orderItem.setCreatedAt(LocalDateTime.now());
        orderItem.setUpdatedAt(LocalDateTime.now());
        orderItem.setCreatedBy("System");
        orderItem.setUpdatedBy("System");
        orderItem.setContent(orderItemRequest.getContent());
        return orderItem;
    }

    public void updateOrder(Long id, OrderRequest orderRequest) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order existingOrder = orderOpt.get();

            Order updatedOrder = Order.builder()
                    .id(existingOrder.getId())
                    .userId(Optional.ofNullable(orderRequest.getUserId()).orElse(existingOrder.getUserId()))
                    .type(Optional.ofNullable(orderRequest.getType()).orElse(existingOrder.getType()))
                    .status(Optional.ofNullable(orderRequest.getStatus()).orElse(existingOrder.getStatus()))
                    .subTotal(Optional.ofNullable(orderRequest.getSubTotal()).orElse(existingOrder.getSubTotal()))
                    .itemDiscount(Optional.ofNullable(orderRequest.getItemDiscount()).orElse(existingOrder.getItemDiscount()))
                    .tax(Optional.ofNullable(orderRequest.getTax()).orElse(existingOrder.getTax()))
                    .shipping(Optional.ofNullable(orderRequest.getShipping()).orElse(existingOrder.getShipping()))
                    .total(Optional.ofNullable(orderRequest.getTotal()).orElse(existingOrder.getTotal()))
                    .promo(Optional.ofNullable(orderRequest.getPromo()).orElse(existingOrder.getPromo()))
                    .discount(Optional.ofNullable(orderRequest.getDiscount()).orElse(existingOrder.getDiscount()))
                    .grandTotal(Optional.ofNullable(orderRequest.getGrandTotal()).orElse(existingOrder.getGrandTotal()))
                    .orderItem(mapToOrderItems(orderRequest.getOrderItem(), existingOrder))
                    .createdBy(existingOrder.getCreatedBy())
                    .updatedBy("System")
                    .createdAt(existingOrder.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            orderRepository.saveAndFlush(updatedOrder);
            log.info("Order {} is updated", updatedOrder.getId());
        }
    }

    private List<OrderItem> mapToOrderItems(List<OrderItemRequest> orderItemRequests, Order existingOrder) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            // Map each OrderItemRequest to OrderItem
            OrderItem orderItem = mapToOrderItem(orderItemRequest, existingOrder);
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}

