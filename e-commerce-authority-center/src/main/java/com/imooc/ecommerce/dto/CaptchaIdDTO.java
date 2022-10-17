package com.imooc.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaIdDTO {
    private String captchaId;
    private String picPath;
}
