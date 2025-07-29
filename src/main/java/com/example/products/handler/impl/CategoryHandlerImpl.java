package com.example.products.handler.impl;

import com.example.common.entity.EnumUtil;
import com.example.common.entity.MessageEvent;
import com.example.common.utilities.ConverterUtil;
import com.example.common.utilities.IdUtil;
import com.example.products.entity.Category;
import com.example.products.entity.Product;
import com.example.products.entity.dto.CategoryDTO;
import com.example.products.entity.dto.ProductDTO;
import com.example.products.handler.CategoryHandler;
import com.example.products.producer.ProductProducer;
import com.example.products.service.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
@Log4j2
public class CategoryHandlerImpl implements CategoryHandler {

    private final ProductProducer productProducer;
    private final CategoryService categoryService;
    private final ConverterUtil converterUtil;
    private final IdUtil idUtil;

    @Override
    public ResponseEntity< Object > createCategory( Category category ) {
        category.setId( this.idUtil.generateId( EnumUtil.UUIDType.SHORT ) );
        Map< String, Object > categoryPayload = this.converterUtil.objectToMap( category );
        MessageEvent messageEvent = new MessageEvent( EnumUtil.EventType.CREATE_CATEGORY, categoryPayload );
        this.productProducer.sendMessage( messageEvent );
        return new ResponseEntity<>( category.getId(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity< Object > updateCategory( Map< String, Object > categoryMap ) {
        return null;
    }

    @Override
    public ResponseEntity< Object > getCategoryById( String id ) {
        Optional< Category > categoryOptional = categoryService.findById( id );
        AtomicReference< CategoryDTO > categoryDTO = new AtomicReference<>( new CategoryDTO() );
        categoryOptional.ifPresentOrElse( category -> {
            CategoryDTO newCategoryDTO = this.converterUtil.transformObject( category, new TypeReference< CategoryDTO >() {
            } );
            categoryDTO.set( /*this.converterUtil.convertObject( category, CategoryDTO.class )*/ new CategoryDTO() );
        }, () -> {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, String.format( "Unable to find Category, Category with ID %s Not Found", id ) );
        } );
        return new ResponseEntity<>( categoryDTO.get(), HttpStatus.FOUND );
    }

    @Override
    public ResponseEntity< Object > deleteCategory( String id ) {
        Map< String, Object > payload = new HashMap<>();
        payload.put( "id", id );
        MessageEvent messageEvent = new MessageEvent( EnumUtil.EventType.DELETE_CATEGORY, payload );
        productProducer.sendMessage( messageEvent );
        return new ResponseEntity<>( payload, HttpStatus.OK );
    }

    @Override
    public ResponseEntity< Object > getProductsByCategory( String categoryId ) {
        Optional< List< Product > > optionalProductList = categoryService.getProductsByCategory( categoryId );
        //return new ResponseEntity<>( optionalProductList.orElse( new ArrayList<>() ), HttpStatus.OK );

        AtomicReference< List< ProductDTO > > productsDTOList = new AtomicReference<>( new ArrayList<>() );

        optionalProductList.ifPresentOrElse( products -> {
            products.forEach( product -> {
                ProductDTO productDTO = this.converterUtil.transformObject( product, new TypeReference< ProductDTO >() {
                } );
                productsDTOList.get().add( productDTO );
            } );
        }, () -> {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, String.format( "Unable to find Products, Category with ID %s Not Found", categoryId ) );
        } );

        return new ResponseEntity<>( productsDTOList, HttpStatus.OK );
    }
}
