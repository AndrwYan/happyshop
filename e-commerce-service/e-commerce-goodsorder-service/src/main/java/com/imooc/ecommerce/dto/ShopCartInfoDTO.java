package com.imooc.ecommerce.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopCartInfoDTO {

    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品数量")
    private Integer nums;

    @ApiModelProperty(value = "是否选中")
    private boolean checked;

}
