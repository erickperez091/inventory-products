package com.example.products.consumer.processor;

import com.example.common.utilities.ConverterUtil;
import com.example.products.entity.Category;
import com.example.products.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class CategoryProcessor {

    private final CategoryService categoryService;
    private final ConverterUtil converterUtil;

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
