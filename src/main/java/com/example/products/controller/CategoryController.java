package com.example.products.controller;

import com.example.products.entity.Category;
import com.example.products.handler.CategoryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping( "/category/v1" )
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger( CategoryController.class );
    private final CategoryHandler categoryHandler;

    @Autowired
    CategoryController( CategoryHandler categoryHandler ) {
        this.categoryHandler = categoryHandler;
    }

    @PostMapping( name = "create", value = "/", path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > create( @RequestBody Category category ) {
        logger.info( "HomeController - Start sending message." );
        return categoryHandler.createCategory( category );
    }

    @PatchMapping( name = "update", value = "/", path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > patch( @RequestBody Map< String, Object > categoryMap ) {
        return categoryHandler.updateCategory( categoryMap );
    }

    @GetMapping( name = "get by id", path = "/{id}", value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > get( @PathVariable( name = "id" ) String id ) {
        return categoryHandler.getCategoryById( id );
    }

    @DeleteMapping( name = "delete", value = "/{id}", path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > delete( @PathVariable( name = "id" ) String id ) {
        return categoryHandler.deleteCategory( id );
    }

    @GetMapping( name = "get products by category id", path = "/{id}/products", value = "/{id}/products", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > getProductsByCategory( @PathVariable( name = "id" ) String id ) {
        return categoryHandler.getProductsByCategory( id );
    }
}
