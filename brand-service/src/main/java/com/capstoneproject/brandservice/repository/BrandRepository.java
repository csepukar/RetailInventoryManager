package com.capstoneproject.brandservice.repository;

import com.capstoneproject.brandservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}