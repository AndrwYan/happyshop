package com.imooc.ecommerce.exception;

/**
 * @Description: 封装Category找不到的异常
 * @Author: yfk
 * @Date:  2022-06-19
 * @return: null
 **/
public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
