package com.example.products.entity;

import com.example.common.entitty.EnumUtil;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@DynamicUpdate
@Entity
@Table( name = "product" )
@SQLDelete( sql = "UPDATE product SET status='DELETED' where id = ?", check = ResultCheckStyle.COUNT )
@FilterDef( name = "productActive" )
@Filters( {
        @Filter( name = "productActive", condition = "status <> 'DELETED'" )
} )
public class Product implements Serializable {

    @Id
    @Column( name = "id", nullable = false, unique = true )
    private String id;

    @Column( name = "description", nullable = false )
    private String description;

    @Column( name = "price", nullable = false )
    private BigDecimal price;

    @Column( name = "totalStock", nullable = false )
    private BigInteger totalStock;

    @Column( name = "minStock", nullable = false )
    private BigInteger minStock;

    @Column( name = "barcode", nullable = false )
    private String barcode;

    @Column( name = "status" )
    @Enumerated( EnumType.STRING )
    private EnumUtil.Status status = defaultStatus();

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "category_id", nullable = false )
    private Category category;

    @Column( name = "discount", nullable = false )
    private BigDecimal discount;

    public Product( String id, String description, BigDecimal price, BigInteger totalStock, BigInteger minStock, String barcode, EnumUtil.Status status, Category category, BigDecimal discount ) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.totalStock = totalStock;
        this.minStock = minStock;
        this.barcode = barcode;
        this.status = status;
        this.category = category;
        this.discount = discount;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice( BigDecimal price ) {
        this.price = price;
    }

    public BigInteger getTotalStock() {
        return totalStock;
    }

    public void setTotalStock( BigInteger totalStock ) {
        this.totalStock = totalStock;
    }

    public BigInteger getMinStock() {
        return minStock;
    }

    public void setMinStock( BigInteger minStock ) {
        this.minStock = minStock;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode( String barcode ) {
        this.barcode = barcode;
    }

    public EnumUtil.Status getStatus() {
        return status;
    }

    public void setStatus( EnumUtil.Status status ) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory( Category category ) {
        this.category = category;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount( BigDecimal discount ) {
        this.discount = discount;
    }

    private EnumUtil.Status defaultStatus() {
        return EnumUtil.Status.ACTIVE;
    }

}
