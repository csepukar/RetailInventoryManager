package com.capstoneproject.productservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // id of product
    private String title;          //name of product eg: fishing Rod
    private String summary;         // eg: tools used for fishing
    private String type;            // type represent category of product: Sports and Entertainment
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private String content;         // store the additional details of the product: only for age +18, card needed to show,etc
}
