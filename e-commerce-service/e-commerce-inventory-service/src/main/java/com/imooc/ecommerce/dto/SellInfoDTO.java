package com.imooc.ecommerce.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 扣减库存实体
 * @Author: yfk
 * @Date: 2022-07-22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellInfoDTO {

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("需要扣减的库存列表")
    private List<GoodsInvInfoDTO> goodsInvInfoDTOS;

}
