package com.capstoneproject.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemLotRequest {
    private int quantity;
    private short sold;
    private boolean returnableItem;
    private int available;
    private short defective;
    private short defectiveCustReturned;
    private LocalDateTime expireDate;
    private ItemRequest item;
}
