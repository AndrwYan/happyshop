package com.imooc.ecommerce.exception;

/**
 * @Description: 商品找不到的异常
 * @Author: yfk
 * @Date:  2022-06-11
 **/
public class GoodsNotFoundException extends RuntimeException {
    public GoodsNotFoundException() {
        super();
    }

    public GoodsNotFoundException(String message) {
        super(message);
    }
}
