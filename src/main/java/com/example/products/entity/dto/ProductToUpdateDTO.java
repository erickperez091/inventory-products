package com.example.products.entity.dto;

public class ProductToUpdateDTO {
    private String id;
    private String description;
    private int units;

    public ProductToUpdateDTO( String id, String description, int units ) {
        this.id = id;
        this.description = description;
        this.units = units;
    }

    public ProductToUpdateDTO() {
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

    public int getUnits() {
        return units;
    }

    public void setUnits( int units ) {
        this.units = units;
    }
}
