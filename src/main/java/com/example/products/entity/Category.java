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
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table( name = "category" )
@Entity
@SQLDelete( sql = "UPDATE category SET status='DELETED' where id = ?", check = ResultCheckStyle.COUNT )
@FilterDef( name = "categoryActive" )
@Filters( {
        @Filter( name = "categoryActive", condition = "status <> 'DELETED'" )
} )
public class Category implements Serializable {

    @Id
    @Column( name = "id", nullable = false, unique = true )
    private String id;

    @Column( name = "description", nullable = false )
    private String description;

    @Column( name = "status" )
    @Enumerated( EnumType.STRING )
    private EnumUtil.Status status = defaultStatus( );

    private EnumUtil.Status defaultStatus ( ) {
        return EnumUtil.Status.ACTIVE;
    }

}
