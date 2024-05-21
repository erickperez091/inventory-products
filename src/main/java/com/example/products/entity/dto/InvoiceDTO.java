package com.example.products.entity.dto;

import java.util.List;

public class InvoiceDTO {
    private String invoiceStatus;
    private List<ProductToUpdateDTO> products;

    public InvoiceDTO() {
    }

    public InvoiceDTO( String invoiceStatus, List< ProductToUpdateDTO > products ) {
        this.invoiceStatus = invoiceStatus;
        this.products = products;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus( String invoiceStatus ) {
        this.invoiceStatus = invoiceStatus;
    }

    public List< ProductToUpdateDTO > getProducts() {
        return products;
    }

    public void setProducts( List< ProductToUpdateDTO > products ) {
        this.products = products;
    }
}
