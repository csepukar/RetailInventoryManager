package com.capstoneproject.orderservice.dto;

import com.capstoneproject.orderservice.model.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private Long itemId;
    private OrderResponse order;
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
