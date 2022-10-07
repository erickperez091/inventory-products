package com.example.products.service;

import com.example.common.entitty.EnumUtil.InvoiceStatus;
import com.example.products.entity.Product;
import com.example.products.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger( ProductService.class );

    private ProductRepository repository;

    @Autowired
    ProductService( ProductRepository productRepository ) {
        this.repository = productRepository;
    }

    @Transactional
    public void save( Product product ) {
        logger.info( "START | Save Product {}", product.getId() );
        repository.save( product );
        logger.info( "FINISH | Save Product {}", product.getId() );
    }

    @Transactional( readOnly = true )
    public Optional< Product > findById( String id ) {
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
            if ( optionalProduct.isPresent() ) {
                Product product = optionalProduct.get();
                long amountProduct = Long.parseLong( map.get( "units" ).toString() );
                switch ( invoiceStatus ) {
                    case APPROVED: {
                        product.setTotalStock( product.getTotalStock().subtract( BigInteger.valueOf( amountProduct ) ) );
                        break;
                    }
                    case CANCELED: {
                        product.setTotalStock( product.getTotalStock().add( BigInteger.valueOf( amountProduct ) ) );
                        break;
                    }
                }
                repository.save( product );
            }
        } );
    }
}
