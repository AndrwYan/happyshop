package com.imooc.ecommerce.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoVO {

    @ApiModelProperty("商品id")
    @NotNull
    private int id;
}
