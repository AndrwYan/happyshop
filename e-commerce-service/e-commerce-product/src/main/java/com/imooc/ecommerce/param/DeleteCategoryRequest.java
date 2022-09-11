package com.imooc.ecommerce.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteCategoryRequest {
    @NotBlank
    private int id;
}
