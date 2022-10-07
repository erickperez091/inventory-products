package com.example.products.consumer.processor;

import com.example.common.utilities.ConverterUtil;
import com.example.products.entity.Category;
import com.example.products.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CategoryProcessor {

    private static final Logger logger = LoggerFactory.getLogger( CategoryProcessor.class );
    private CategoryService categoryService;
    private ConverterUtil converterUtil;

    @Autowired
    public CategoryProcessor( CategoryService categoryService, ConverterUtil converterUtil ) {
        this.categoryService = categoryService;
        this.converterUtil = converterUtil;
    }

    public void store( Map< String, Object > payload ) {
        logger.info( "START | Create Category {}", payload );
        Category category = converterUtil.mapToObject( payload, Category.class );
        categoryService.save( category );
        logger.info( "FINISH | Create Category {}", payload );
    }

    public void refresh( Map< String, Object > payload ) {
        logger.info( "START | Update Product {}", payload );
        Category category = converterUtil.mapToObject( payload, Category.class );
        Category categoryFromDb = categoryService.findById( category.getId() ).get();
        converterUtil.copyProperties( category, categoryFromDb );
        categoryService.save( categoryFromDb );
        logger.info( "FINISH | Update Product {}", payload );
    }

    public void delete( Map< String, Object > payload ) {
        logger.info( "START | Delete Category {}", payload );
        String id = (String) payload.get( "id" );
        categoryService.delete( id );
        logger.info( "FINISH | Delete Category {}", payload );
    }

}
