package com.imooc.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    @NotNull
    private String nickName;

    @NotNull
    private String password;

    @NotNull
    private String mobile;
}
