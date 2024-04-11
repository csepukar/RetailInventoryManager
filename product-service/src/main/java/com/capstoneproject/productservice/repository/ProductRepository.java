package com.capstoneproject.productservice.repository;

import com.capstoneproject.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>{
}
