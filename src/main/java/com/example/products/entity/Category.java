package com.example.products.entity;

import com.example.common.entity.EnumUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@DynamicUpdate
@Table( name = "category" )
@Entity
@SQLDelete( sql = "UPDATE category SET status='DELETED' where id = ?", check = ResultCheckStyle.COUNT )
@FilterDef( name = "categoryActive" )
@Filters( {
        @Filter( name = "categoryActive", condition = "status <> 'DELETED'" )
} )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    @Column( name = "id", nullable = false, unique = true )
    private String id;

    @Column( name = "description", nullable = false )
    private String description;

    @Column( name = "status" )
    @Enumerated( EnumType.STRING )
    private EnumUtil.Status status = defaultStatus();

    private EnumUtil.Status defaultStatus() {
        return EnumUtil.Status.ACTIVE;
    }

    @Column(name = "createdBy", updatable = false)
    private String createdBy;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss", iso = DATE_TIME )
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" )
    @Column( name = "createdAt", columnDefinition = "TIMESTAMP" )
    @JsonDeserialize( using = LocalDateTimeDeserializer.class )
    @JsonSerialize( using = LocalDateTimeSerializer.class )
    private LocalDateTime createdAt;
}

