package com.example.products.consumer.processor;

import com.example.common.entitty.EnumUtil;
import com.example.common.entitty.EnumUtil.InvoiceStatus;
import com.example.common.utilities.ConverterUtil;
import com.example.products.entity.Product;
import com.example.products.entity.dto.InvoiceDTO;
import com.example.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductProcessor {

    private static final Logger logger = LoggerFactory.getLogger( ProductProcessor.class );

    private final ProductService productService;
    private final ConverterUtil converterUtil;

    @Autowired
    public ProductProcessor( ProductService productService, ConverterUtil converterUtil ) {
        this.productService = productService;
        this.converterUtil = converterUtil;
    }

    public void store( Map< String, Object > payload ) {
        logger.info( "START | Create Product {}", payload );
        Product product = converterUtil.mapToObject( payload, Product.class );
        productService.save( product );
        logger.info( "FINISH | Create Product {}", payload );
    }

    public void refresh( Map< String, Object > payload ) {
        logger.info( "START | Update Product {}", payload );
        Product product = converterUtil.mapToObject( payload, Product.class );
        Product productFromDb = productService.findById( product.getId() ).get();
        converterUtil.copyProperties( product, productFromDb );
        productService.save( productFromDb );
        logger.info( "FINISH | Update Product {}", payload );
    }

    public void delete( Map< String, Object > payload ) {
        logger.info( "START | Delete Product {}", payload );
        String id = (String) payload.get( "id" );
        productService.delete( id );
        logger.info( "FINISH | Delete Product {}", payload );
    }

    public void updateProductsStock( Map< String, Object > payload ) {
        logger.info( "START | Update Products Stock" );
        InvoiceDTO invoiceDTO = this.converterUtil.mapToObject( payload, InvoiceDTO.class );
        InvoiceStatus invoiceStatus = InvoiceStatus.valueOf( invoiceDTO.getInvoiceStatus() );
        switch ( invoiceStatus ){
            case APPROVED: {
                invoiceDTO.getProducts().stream().forEach( productDTO ->{
                    Optional<Product> productOptional = productService.findById( productDTO.getId() );
                    if( productOptional.isPresent() ){
                        Product product = productOptional.get();
                        product.setTotalStock( product.getTotalStock().subtract( new BigInteger( String.valueOf( productDTO.getUnits() ) ) ) );
                        productService.save( product );
                    }
                });
                break;
            }
            case CANCELED: {
                invoiceDTO.getProducts().stream().forEach( productDTO ->{
                    Optional<Product> productOptional = productService.findById( productDTO.getId() );
                    if( productOptional.isPresent() ){
                        Product product = productOptional.get();
                        product.setTotalStock( product.getTotalStock().add( new BigInteger( String.valueOf( productDTO.getUnits() ) ) ) );
                        productService.save( product );
                    }
                });
                break;
            }
        }
        logger.info( "FINISH | Update Products Stock" );
    }
}
