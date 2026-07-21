package com.example.products.entity.dto;

import com.example.common.entity.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private String id;
    private String description;
    private String status = defaultStatus();

    private String defaultStatus() {
        return EnumUtil.Status.ACTIVE.name();
    }

}
