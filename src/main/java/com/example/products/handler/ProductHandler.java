package com.example.products.handler;

import com.example.products.entity.dto.InvoiceDTO;
import com.example.products.entity.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProductHandler {

    ResponseEntity< Object > createProduct( ProductDTO product );

    ResponseEntity< Object > updateProduct( Map< String, Object > productMap );

    ResponseEntity< Object > getProductById( String id );

    ResponseEntity< Object > deleteProduct( String id );

    ResponseEntity< Object > updateProductsStock( InvoiceDTO invoiceDTO );
}
