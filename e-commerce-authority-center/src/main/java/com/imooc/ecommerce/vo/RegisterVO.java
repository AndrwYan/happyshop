package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVO {

    @NotBlank
    @Size(min = 11,max = 11)
    private String mobile;

    @NotBlank
    @Size(min = 4,max = 4)
    private String code;

    @NotBlank
    private String password;
}
