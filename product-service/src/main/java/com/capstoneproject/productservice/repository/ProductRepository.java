package com.capstoneproject.productservice.repository;

import com.capstoneproject.productservice.model.Product;
import com.capstoneproject.productservice.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>{
}
