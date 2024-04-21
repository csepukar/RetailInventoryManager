package com.capstoneproject.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
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
