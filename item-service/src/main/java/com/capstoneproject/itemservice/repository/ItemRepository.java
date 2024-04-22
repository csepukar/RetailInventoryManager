package com.capstoneproject.itemservice.repository;

import com.capstoneproject.itemservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
    boolean existsByIdAndQuantityIsGreaterThanEqual(Long id, int quantity);
}
