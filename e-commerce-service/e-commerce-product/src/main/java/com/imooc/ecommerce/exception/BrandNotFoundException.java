package com.imooc.ecommerce.exception;

/**
 * @Description: 封装Brand找不到的异常
 * @Author: yfk
 * @Date:  2022-06-19
 * @return: null
 **/
public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException() {
        super();
    }

    public BrandNotFoundException(String message) {
        super(message);
    }
}
