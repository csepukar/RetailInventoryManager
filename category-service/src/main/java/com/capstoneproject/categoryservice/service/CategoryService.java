package com.capstoneproject.categoryservice.service;

import com.capstoneproject.categoryservice.dto.CategoryRequest;
import com.capstoneproject.categoryservice.dto.CategoryResponse;
import com.capstoneproject.categoryservice.model.Category;
import com.capstoneproject.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryRequest categoryRequest) {
        Category parentCategory = null;
        if (categoryRequest.getParentId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryRequest.getParentId());
            if (optionalCategory.isPresent()) {
                parentCategory = optionalCategory.get();
                categoryRequest.setIsRootNode(false);
            } else {
                categoryRequest.setIsRootNode(true);
            }
        } else {
            categoryRequest.setIsRootNode(true);
        }
        Category category = Category.builder()
                .title(categoryRequest.getTitle())
                .isRootNode(categoryRequest.getIsRootNode())
                .parentCategory(parentCategory)
                .build();
        category.setCreatedBy("ram"); //need to implement login and name should come from userid
        category.setCreatedAt(LocalDateTime.now());
        categoryRepository.save(category);
        log.info("Category {} is saved", category.getId());
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(this::mapToCategoryResponse).toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.isPresent() ? optionalCategory.map(this::mapToCategoryResponse).get() : null;
    }

    public void updateCategory(Long id, CategoryRequest categoryRequest) {
        categoryRepository.findById(id).ifPresent(category -> {
            Category parentCategory = null;
            if (categoryRequest.getParentId() != null) {
                parentCategory = categoryRepository.findById(categoryRequest.getParentId()).orElse(null);
            }
            categoryRequest.setIsRootNode(parentCategory == null);
            Category updatedCategory = Category.builder()
                    .id(category.getId())
                    .title(Optional.ofNullable(categoryRequest.getTitle()).orElse(category.getTitle()))
                    .isRootNode(Optional.ofNullable(categoryRequest.getIsRootNode()).orElse(category.getIsRootNode()))
                    .parentCategory(parentCategory)
                    .createdBy(category.getCreatedBy())
                    .updatedBy("shyam") // change the name with the login credential
                    .createdAt(category.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            categoryRepository.saveAndFlush(updatedCategory);
            log.info("Category {} is updated", updatedCategory.getId());
        });
    }

    public CategoryResponse getAllTree() {
        Category category = categoryRepository.findByIsRootNode(true);
        return recursiveList(category);
    }

    private CategoryResponse recursiveList(Category category){
        List<Category> categoryList = categoryRepository.findAllByParentCategory(category);
        if(categoryList.isEmpty()){
            return mapToCategoryResponse(category);
        }else{
            List<CategoryResponse> childResponses = new ArrayList<>(); // Create a new list to hold child responses
            for(Category childCategory : categoryList) { // Iterate through categoryList
                childResponses.add(recursiveList(childCategory)); // Recursively call recursiveList for each child category
            }
            CategoryResponse result = mapToCategoryResponse(category);
            result.setChildCategory(childResponses); // Set the child responses to the result
            return result;
        }
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .isRootNode(category.getIsRootNode())
                .parentId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .build();
    }
}
