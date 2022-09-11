package com.imooc.ecommerce.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 实体
 * @Author: yfk
 * @Date:  2022-07-22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInvInfoDTO {

    @ApiModelProperty("商品id")
    private int goodsId;

    @ApiModelProperty("扣减的数量")
    private int number;
}
