package com.imooc.ecommerce.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInvInfoVO {

    @ApiModelProperty("商品id")

    private Integer goodsId;

    @ApiModelProperty("商品数量")
    private Integer numbers;

}
