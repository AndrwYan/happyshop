package com.imooc.ecommerce.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellDetailDTO {

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("订单状态")
    private int status;

}
