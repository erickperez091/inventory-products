package com.example.products.handler.impl;

import com.example.common.entity.EnumUtil.EventType;
import com.example.common.entity.EnumUtil.UUIDType;
import com.example.common.entity.MessageEvent;
import com.example.common.utilities.ConverterUtil;
import com.example.common.utilities.IdGeneratorService;
import com.example.products.entity.Product;
import com.example.products.entity.dto.InvoiceDTO;
import com.example.products.entity.dto.ProductDTO;
import com.example.products.handler.ProductHandler;
import com.example.products.messaging.ProductPublisher;
import com.example.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class ProductHandlerImpl implements ProductHandler {

    private final ProductService productService;
    private final ProductPublisher productPublisher;
    private final ConverterUtil converterUtil;
    private final IdGeneratorService idGeneratorService;

    @Override
    public ResponseEntity<Object> createProduct(ProductDTO product) {
        product.setId(this.idGeneratorService.generateId(UUIDType.SHORT));
        Map<String, Object> productPayload = converterUtil.objectToMap(product);
        MessageEvent messageEvent = new MessageEvent(EventType.CREATE_PRODUCT, productPayload);
        this.productPublisher.sendEvent(messageEvent);
        return new ResponseEntity<>(product.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateProduct(Map<String, Object> productMap) {
        if (!productMap.containsKey("id")) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Unable to update Product, 'ID' key not present\"");
            //return new ResponseEntity<>("Unable to update Product, \"ID\" key not present", HttpStatus.NOT_ACCEPTABLE);
        }
        String productId = (String) productMap.get("id");
        Optional<Product> optionalProduct = productService.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to update Product, Product with ID %s Not Found", productId));
            //return new ResponseEntity<>(String.format("Unable to update Product, Product with ID %s Not Found", productId), HttpStatus.NOT_FOUND);
        }
        MessageEvent messageEvent = new MessageEvent(EventType.UPDATE_PRODUCT, productMap);
        this.productPublisher.sendEvent(messageEvent);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getProductById(String id) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find Product, Product with ID %s Not Found", id));
        }
        Product product = productOptional.get();
        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<Object> deleteProduct(String id) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);
        MessageEvent messageEvent = new MessageEvent(EventType.DELETE_PRODUCT, payload);
        this.productPublisher.sendEvent(messageEvent);
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateProductsStock(InvoiceDTO invoiceDTO) {
        Map<String, Object> invoiceDTOMap = this.converterUtil.objectToMap(invoiceDTO);
        MessageEvent messageEvent = new MessageEvent(EventType.UPDATE_PRODUCT_STOCK, invoiceDTOMap);
        this.productPublisher.sendEvent(messageEvent);
        return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
    }
}
