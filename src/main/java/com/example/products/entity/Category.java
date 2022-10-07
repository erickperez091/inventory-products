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
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
    private EnumUtil.Status status = defaultStatus();

    public Category( String id, String description, EnumUtil.Status status ) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public Category() {
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

    public EnumUtil.Status getStatus() {
        return status;
    }

    public void setStatus( EnumUtil.Status status ) {
        this.status = status;
    }

    private EnumUtil.Status defaultStatus() {
        return EnumUtil.Status.ACTIVE;
    }
}
