package com.imooc.ecommerce.exception;

/**
 * @Description: 封装商品分类找不到的异常
 * @Author: yfk
 * @Date:  2022-06-11
 **/
public class GoodsCategoryNotFoundException extends RuntimeException {
    public GoodsCategoryNotFoundException() {
        super();
    }

    public GoodsCategoryNotFoundException(String message) {
        super(message);
    }
}
