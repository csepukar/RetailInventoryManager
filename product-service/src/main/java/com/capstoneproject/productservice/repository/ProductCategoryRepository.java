package com.capstoneproject.productservice.repository;

import com.capstoneproject.productservice.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long>{
    void deleteAllByProductId(Long productId);
}