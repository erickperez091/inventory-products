package com.example.products.repository;

import com.example.products.entity.Category;
import com.example.products.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CustomRepository {

    Optional< Product > findProductById( String id );

    Optional< Category > findCategoryById( String id );

    Optional< List< Product > > findProductsByCategory( String categoryId );

}
