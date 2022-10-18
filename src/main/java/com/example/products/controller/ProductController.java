package com.example.products.controller;

import com.example.products.entity.Product;
import com.example.products.entity.dto.InvoiceDTO;
import com.example.products.entity.dto.ProductToUpdateDTO;
import com.example.products.handler.ProductHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( "/product/v1" )
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger( ProductController.class );
    private final ProductHandler productHandler;

    @Autowired
    ProductController( ProductHandler productHandler ) {
        this.productHandler = productHandler;
    }

    @GetMapping( value = "ping" )
    public ResponseEntity< String > ping() {
        return new ResponseEntity<>( "Microservice is up and running", HttpStatus.OK );
    }

    @PostMapping( name = "create", value = "/", path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > create( @RequestBody Product product ) {
        logger.info( "HomeController - Start sending message." );
        return productHandler.createProduct( product );
    }

    @PatchMapping( name = "update", value = "/", path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > patch( @RequestBody Map< String, Object > productMap ) {
        return productHandler.updateProduct( productMap );
    }

    @GetMapping( name = "get by id", path = "/{id}", value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > get( @PathVariable( name = "id" ) String id ) {
        return productHandler.getProductById( id );
    }

    @DeleteMapping( name = "delete", value = "/{id}", path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > delete( @PathVariable( name = "id" ) String id ) {
        return productHandler.deleteProduct( id );
    }

    @PatchMapping( name = "update products stock", value = "/products-stock", path = "/products-stock", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity< Object > updateProductsStock( @RequestBody InvoiceDTO invoiceDTO ) {
        return productHandler.updateProductsStock( invoiceDTO );
    }
}
