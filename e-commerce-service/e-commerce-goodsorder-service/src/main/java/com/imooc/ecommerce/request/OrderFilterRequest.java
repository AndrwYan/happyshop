package com.imooc.ecommerce.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilterRequest {

    private Integer userId;

    private Integer pages;

    private Integer pagePerNums;

}
