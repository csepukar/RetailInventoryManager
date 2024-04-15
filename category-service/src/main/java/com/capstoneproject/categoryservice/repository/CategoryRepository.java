package com.capstoneproject.categoryservice.repository;

import com.capstoneproject.categoryservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByIsRootNode(Boolean isRootNode);
    List<Category> findAllByParentCategory(Category category);
}
