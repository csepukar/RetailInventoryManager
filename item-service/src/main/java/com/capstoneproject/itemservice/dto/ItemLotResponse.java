package com.capstoneproject.itemservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ItemLotResponse {
    private Long id;
    private short quantity;
    private short sold;
    private boolean returnableItem;
    private short available;
    private short defective;
    private short defectiveCustReturned;
    private LocalDateTime expireDate;
    private ItemLotResponse item;
}
