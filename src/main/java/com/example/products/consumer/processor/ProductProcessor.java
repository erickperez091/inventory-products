package com.example.products.consumer.processor;

import com.example.common.entitty.EnumUtil.InvoiceStatus;
import com.example.common.utilities.ConverterUtil;
import com.example.products.entity.Product;
import com.example.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductProcessor {

    private static final Logger logger = LoggerFactory.getLogger( ProductProcessor.class );

    @Autowired
    private ProductService productService;

    public void store ( Map<String, Object> payload ) {
        logger.info( "START | Create Product {}", payload );
        Product product = ConverterUtil.mapToObject( payload, Product.class );
        productService.save( product );
        logger.info( "FINISH | Create Product {}", payload );
    }

    public void refresh ( Map<String, Object> payload ) {
        logger.info( "START | Update Product {}", payload );
        Product product = ConverterUtil.mapToObject( payload, Product.class );
        Product productFromDb = productService.findById( product.getId( ) ).get( );
        ConverterUtil.copyProperties( product, productFromDb );
        productService.save( productFromDb );
        logger.info( "FINISH | Update Product {}", payload );
    }

    public void delete ( Map<String, Object> payload ) {
        logger.info( "START | Delete Product {}", payload );
        String id = ( String ) payload.get( "id" );
        productService.delete( id );
        logger.info( "FINISH | Delete Product {}", payload );
    }

    public void updateProductsStock ( Map<String, Object> payload ) {
        logger.info( "START | Update Products Stock" );
        List<HashMap<String, Object>> productsListPayload = ( List<HashMap<String, Object>> ) payload.get( "products" );
        InvoiceStatus invoiceStatus = InvoiceStatus.valueOf( payload.get( "invoiceStatus" ).toString( ) );
        productService.updateProductStock( productsListPayload, invoiceStatus );
        logger.info( "FINISH | Update Products Stock" );
    }
}
