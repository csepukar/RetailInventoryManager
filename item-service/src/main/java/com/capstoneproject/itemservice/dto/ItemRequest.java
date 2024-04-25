package com.capstoneproject.itemservice.dto;

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
public class ItemRequest {
    private Long brandId;
    private Long productId;
    private String itemName;
    private String sku;
    private Float mrp;
    private String unit;
    private String dimension;
    private Integer weight;
    private Long mpn;
    private Long isbn;
    private Long upc;
    private Long ean;
    private Boolean isActive;
    private Integer minRecomStock;
    private List<ItemLotRequest> itemLots;
}
