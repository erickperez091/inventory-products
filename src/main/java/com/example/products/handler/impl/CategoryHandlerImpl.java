package com.example.products.handler.impl;

import com.example.common.entitty.EnumUtil;
import com.example.common.entitty.MessageEvent;
import com.example.common.utilities.ConverterUtil;
import com.example.common.utilities.IdUtil;
import com.example.products.entity.Category;
import com.example.products.entity.Product;
import com.example.products.handler.CategoryHandler;
import com.example.products.producer.ProductProducer;
import com.example.products.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CategoryHandlerImpl implements CategoryHandler {

    private ProductProducer productProducer;
    private CategoryService categoryService;
    private ConverterUtil converterUtil;
    private IdUtil idUtil;

    @Autowired
    CategoryHandlerImpl(ProductProducer productProducer, CategoryService categoryService, ConverterUtil converterUtil, IdUtil idUtil) {
        this.productProducer = productProducer;
        this.categoryService = categoryService;
        this.converterUtil = converterUtil;
        this.idUtil = idUtil;
    }

    @Override
    public ResponseEntity<Object> createCategory(Category category) {
        category.setId(idUtil.generateId(EnumUtil.UUIDType.SHORT));
        Map<String, Object> categoryPayload = converterUtil.objectToMap(category);
        MessageEvent messageEvent = new MessageEvent(EnumUtil.EventType.CREATE_CATEGORY, categoryPayload);
        productProducer.sendMessage(messageEvent);
        return new ResponseEntity<>(category.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateCategory(Map<String, Object> categoryMap) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getCategoryById(String id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find Category, Category with ID %s Not Found", id));
        }
        Category category = categoryOptional.get();
        return new ResponseEntity<>(category, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<Object> deleteCategory(String id) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);
        MessageEvent messageEvent = new MessageEvent(EnumUtil.EventType.DELETE_CATEGORY, payload);
        productProducer.sendMessage(messageEvent);
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getProductsByCategory(String categoryId) {
        Optional<List<Product>> optionalProductList = categoryService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(optionalProductList.orElse(new ArrayList<>()), HttpStatus.OK);
    }
}
