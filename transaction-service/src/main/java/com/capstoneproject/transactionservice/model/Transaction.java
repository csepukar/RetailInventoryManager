package com.capstoneproject.transactionservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long orderId;
    private String code;
    private Integer type;
    private Integer mode;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String content;
    private String createdBy;
    private String updatedBy;
}
