package com.imooc.ecommerce.dto;

import lombok.Data;

@Data
public class CreateUserDTO {

    private String nickName;

    private String password;

    private String mobile;
}
