package com.capstoneproject.orderservice.dto;

import com.capstoneproject.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {
    private Long itemId;
    private OrderRequest order;
    private String sku;
    private float price;
    private float discount;
    private int orderedQuantity;
    private Integer arrivedQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private String content;

}
