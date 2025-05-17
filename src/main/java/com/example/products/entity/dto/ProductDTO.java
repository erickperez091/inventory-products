package com.example.products.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String id;
    private String description;
    private BigDecimal price;
    private BigInteger totalStock;
    private BigInteger minStock;
    private String barcode;
    private String status;
    private String categoryId;
    private BigDecimal discount;

}
