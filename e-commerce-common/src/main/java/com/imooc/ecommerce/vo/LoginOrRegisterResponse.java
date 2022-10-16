package com.imooc.ecommerce.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginOrRegisterResponse {

    private Long id;
    private String nickName;
    private String token;
    private String expired;

}
