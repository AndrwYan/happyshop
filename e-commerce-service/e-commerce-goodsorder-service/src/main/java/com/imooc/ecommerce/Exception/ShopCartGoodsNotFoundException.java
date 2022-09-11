package com.imooc.ecommerce.Exception;

/**
 * @Description: 商品找不到的异常
 * @Author: yfk
 * @Date:  2022-06-11
 **/
public class ShopCartGoodsNotFoundException extends RuntimeException {
    public ShopCartGoodsNotFoundException() {
        super();
    }

    public ShopCartGoodsNotFoundException(String message) {
        super(message);
    }
}
