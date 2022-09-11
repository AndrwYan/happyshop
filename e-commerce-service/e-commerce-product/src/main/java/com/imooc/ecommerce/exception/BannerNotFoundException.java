package com.imooc.ecommerce.exception;

/**
 * @Description: 封装Brand找不到的异常
 * @Author: yfk
 * @Date:  2022-06-19
 * @return: null
 **/
public class BannerNotFoundException extends RuntimeException{
    public BannerNotFoundException() {
        super();
    }

    public BannerNotFoundException(String message) {
        super(message);
    }
}
