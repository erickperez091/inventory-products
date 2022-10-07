package com.example.products.handler.impl;

import com.example.common.entitty.EnumUtil.EventType;
import com.example.common.entitty.EnumUtil.UUIDType;
import com.example.common.entitty.MessageEvent;
import com.example.common.utilities.ConverterUtil;
import com.example.common.utilities.IdUtil;
import com.example.products.entity.Product;
import com.example.products.handler.ProductHandler;
import com.example.products.producer.ProductProducer;
import com.example.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductHandlerImpl implements ProductHandler {

    private ProductService productService;
    private ProductProducer productProducer;
    private ConverterUtil converterUtil;
    private IdUtil idUtil;

    @Autowired
    ProductHandlerImpl( ProductService productService, ProductProducer productProducer, ConverterUtil converterUtil, IdUtil idUtil ) {
        this.productService = productService;
        this.productProducer = productProducer;
        this.converterUtil = converterUtil;
        this.idUtil = idUtil;
    }

    @Override
    public ResponseEntity< Object > createProduct( Product product ) {
        product.setId( idUtil.generateId( UUIDType.SHORT ) );
        Map< String, Object > productPayload = converterUtil.objectToMap( product );
        MessageEvent messageEvent = new MessageEvent( EventType.CREATE_PRODUCT, productPayload );
        productProducer.sendMessage( messageEvent );
        return new ResponseEntity<>( product.getId(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity< Object > updateProduct( Map< String, Object > productMap ) {
        if ( !productMap.containsKey( "id" ) ) {
            throw new ResponseStatusException( HttpStatus.NOT_ACCEPTABLE, "Unable to update Product, 'ID' key not present\"" );
            //return new ResponseEntity<>("Unable to update Product, \"ID\" key not present", HttpStatus.NOT_ACCEPTABLE);
        }
        String productId = (String) productMap.get( "id" );
        Optional< Product > optionalProduct = productService.findById( productId );
        if ( optionalProduct.isEmpty() ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, String.format( "Unable to update Product, Product with ID %s Not Found", productId ) );
            //return new ResponseEntity<>(String.format("Unable to update Product, Product with ID %s Not Found", productId), HttpStatus.NOT_FOUND);
        }
        MessageEvent messageEvent = new MessageEvent( EventType.UPDATE_PRODUCT, productMap );
        productProducer.sendMessage( messageEvent );
        return new ResponseEntity<>( productId, HttpStatus.OK );
    }

    @Override
    public ResponseEntity< Object > getProductById( String id ) {
        Optional< Product > productOptional = productService.findById( id );
        if ( productOptional.isEmpty() ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, String.format( "Unable to find Product, Product with ID %s Not Found", id ) );
        }
        Product product = productOptional.get();
        return new ResponseEntity<>( product, HttpStatus.FOUND );
    }

    @Override
    public ResponseEntity< Object > deleteProduct( String id ) {
        Map< String, Object > payload = new HashMap<>();
        payload.put( "id", id );
        MessageEvent messageEvent = new MessageEvent( EventType.DELETE_PRODUCT, payload );
        productProducer.sendMessage( messageEvent );
        return new ResponseEntity<>( payload, HttpStatus.OK );
    }

    @Override
    public ResponseEntity< Object > updateProductsStock( Map< String, Object > products ) {
        MessageEvent messageEvent = new MessageEvent( EventType.UPDATE_PRODUCT_STOCK, products );
        productProducer.sendMessage( messageEvent );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
