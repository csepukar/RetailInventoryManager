package com.capstoneproject.itemservice.repository;

import com.capstoneproject.itemservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    boolean existsByIdAndQuantityIsGreaterThanEqual(Long id, int quantity);
    List<Item> getAllByIsActiveTrue();
}
