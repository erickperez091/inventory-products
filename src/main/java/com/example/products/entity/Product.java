package com.example.products.entity;

import com.example.common.entitty.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private EnumUtil.Status status = defaultStatus( );

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "category_id", nullable = false )
    private Category category;

    @Column( name = "discount", nullable = false )
    private BigDecimal discount;

    private EnumUtil.Status defaultStatus ( ) {
        return EnumUtil.Status.ACTIVE;
    }

}
