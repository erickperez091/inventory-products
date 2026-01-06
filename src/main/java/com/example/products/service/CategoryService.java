package com.example.products.service;

import com.example.products.entity.Category;
import com.example.products.entity.Product;
import com.example.products.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional
    public void save(Category category) {
        logger.info("START | Save Category {}", category.getId());
        repository.save(category);
        logger.info("FINISH | Save Product {}", category.getId());
    }

    @Transactional(readOnly = true)
    public Optional<Category> findById(String id) {
        return repository.findCategoryById(id);

    }

    @Transactional
    public void delete(String id) {
        logger.info("START | Delete Category {}", id);
        repository.deleteById(id);
        logger.info("FINISH | Delete Product {}", id);
    }

    @Transactional(readOnly = true)
    public Optional<List<Product>> getProductsByCategory(String categoryId) {
        return repository.findProductsByCategory(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return repository.findAll();
    }
}
