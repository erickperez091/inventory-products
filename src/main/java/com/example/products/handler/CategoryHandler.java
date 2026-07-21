package com.example.products.handler;

import com.example.products.entity.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoryHandler {

    ResponseEntity<Object> createCategory(CategoryDTO category);

    ResponseEntity<Object> updateCategory(Map<String, Object> categoryMap);

    ResponseEntity<Object> getCategoryById(String id);

    ResponseEntity<Object> deleteCategory(String id);

    ResponseEntity<Object> getProductsByCategory(String categoryId);

    ResponseEntity<Object> getAllCategories();
}
