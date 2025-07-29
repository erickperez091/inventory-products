package com.example.products.service;

import com.example.common.entity.EnumUtil.InvoiceStatus;
import com.example.products.entity.Product;
import com.example.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {
    private final ProductRepository repository;

    @Transactional
    public void save( Product product ) {
        logger.info( "START | Save Product {}", product.getId() );
        repository.save( product );
        logger.info( "FINISH | Save Product {}", product.getId() );
    }

    @Transactional( readOnly = true )
    public Optional< Product > findById( String id ) {
        /*
                Optional< Product > result = repository.findProductById( id );
        return result.map( product ->
                new ProductDTO( product.getId(), product.getDescription(), product.getPrice(), product.getTotalStock(),
                        product.getMinStock(), product.getBarcode(), product.getStatus().name(), product.getCategory().getId(), product.getDiscount()) )
                .orElseThrow(() -> new RuntimeException( "Product not found" ) );
        * */
        return repository.findProductById( id );
    }

    public void delete( String id ) {
        logger.info( "START | Delete Product {}", id );
        repository.deleteById( id );
        logger.info( "FINISH | Delete Product {}", id );
    }

    @Transactional
    public void updateProductStock( List< HashMap< String, Object > > productStock, InvoiceStatus invoiceStatus ) {
        productStock.forEach( map -> {
            Optional< Product > optionalProduct = repository.findProductById( map.get( "id" ).toString() );
            optionalProduct.ifPresentOrElse( product -> {
                long amountProduct = Long.parseLong( map.get( "units" ).toString() );
                switch ( invoiceStatus ) {
                    case APPROVED -> {
                        product.setTotalStock( product.getTotalStock().subtract( BigInteger.valueOf( amountProduct ) ) );
                    }
                    case CANCELED -> {
                        product.setTotalStock( product.getTotalStock().add( BigInteger.valueOf( amountProduct ) ) );
                    }
                }
                repository.save( product );
            }, () -> {

            } );
        } );
    }
}
