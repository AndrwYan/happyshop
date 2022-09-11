package com.imooc.ecommerce.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

    private Integer id;

    @ApiModelProperty(value = "用户id")
    @NotBlank
    private Integer userId;

    @ApiModelProperty(value = "商品id")
    @NotBlank
    private Integer goodsId;

    @ApiModelProperty(value = "商品数量")
    @NotBlank
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    @NotBlank
    private String goodsImage;

    @ApiModelProperty(value = "商品单价")
    @NotBlank
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品数量")
    @NotBlank
    private Integer nums;

    @ApiModelProperty(value = "是否选中")
    @NotBlank
    private boolean checked;
}
