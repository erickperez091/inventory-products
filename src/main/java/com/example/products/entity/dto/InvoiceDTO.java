package com.example.products.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private String invoiceStatus;
    private List<ProductToUpdateDTO> products;
}
