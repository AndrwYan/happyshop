package com.imooc.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Integer id;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图片
     */
    private String goodsImage;
    /**
     * 商品价格
     */
    private Float goodsPrice;
    /**
     * 购买的商品数量
     */
    private Integer nums;
}
