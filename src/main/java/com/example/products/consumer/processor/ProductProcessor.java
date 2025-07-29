package com.example.products.consumer.processor;

import com.example.common.entity.EnumUtil.InvoiceStatus;
import com.example.common.utilities.ConverterUtil;
import com.example.products.entity.Product;
import com.example.products.entity.dto.InvoiceDTO;
import com.example.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class ProductProcessor {


    private final ProductService productService;
    private final ConverterUtil converterUtil;

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
        switch ( invoiceStatus ) {
            case APPROVED -> {
                invoiceDTO.getProducts().forEach( productDTO -> {
                    Optional< Product > productOptional = productService.findById( productDTO.getId() );
                    productOptional.ifPresentOrElse( product -> {
                        product.setTotalStock( product.getTotalStock().subtract( new BigInteger( String.valueOf( productDTO.getUnits() ) ) ) );
                        productService.save( product );
                    }, () -> {
                        logger.info( "Product {} not found", productDTO.getId() );
                    } );
                } );
            }
            case CANCELED -> {
                invoiceDTO.getProducts().forEach( productDTO -> {
                    Optional< Product > productOptional = productService.findById( productDTO.getId() );
                    productOptional.ifPresentOrElse( product -> {
                        product.setTotalStock( product.getTotalStock().add( new BigInteger( String.valueOf( productDTO.getUnits() ) ) ) );
                        productService.save( product );
                    }, () -> {
                        logger.info( "Product {} not found", productDTO.getId() );
                    } );
                } );
            }
        }
        logger.info( "FINISH | Update Products Stock" );
    }
}
