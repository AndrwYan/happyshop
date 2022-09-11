package com.imooc.ecommerce.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {

    @ApiModelProperty("用户id")
    @NotBlank(message = "userId格式不正确")
    private String userId;

}
