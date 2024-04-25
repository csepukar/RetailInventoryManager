package com.capstoneproject.orderservice.dto;

import com.capstoneproject.orderservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long userId;
    private int type;
    private int status;
    private float subTotal;
    private float itemDiscount;
    private float tax;
    private float shipping;
    private float total;
    private String promo;
    private float discount;
    private float grandTotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private String content;
    private List<OrderItemRequest> orderItem;
}
