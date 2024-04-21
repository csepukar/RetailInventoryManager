package com.capstoneproject.itemservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item",cascade = CascadeType.ALL)
    private List<ItemLot> itemLots;
}
