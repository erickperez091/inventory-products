package com.example.products.handler;

import com.example.products.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoryHandler {

    ResponseEntity<Object> createCategory ( Category category );

    ResponseEntity<Object> updateCategory ( Map<String, Object> categoryMap );

    ResponseEntity<Object> getCategoryById ( String id );

    ResponseEntity<Object> deleteCategory ( String id );

    ResponseEntity<Object> getProductsByCategory ( String categoryId );
}
