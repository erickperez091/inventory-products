package com.example.products.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductToUpdateDTO {
    private String id;
    private String description;
    private int units;
}
