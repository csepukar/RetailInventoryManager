package com.capstoneproject.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem;
}
