package com.imooc.ecommerce.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SmsVO {

    @NotBlank
    private String phone;
}
