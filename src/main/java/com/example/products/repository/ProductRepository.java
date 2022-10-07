package com.example.products.repository;

import com.example.products.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository< Product, String >, CustomRepository {
}
