package com.capstoneproject.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String title;
    private String summary;
    private String type;
    private String content;
    private List<ProductCategoryRequest> productCategoryLink;
}
