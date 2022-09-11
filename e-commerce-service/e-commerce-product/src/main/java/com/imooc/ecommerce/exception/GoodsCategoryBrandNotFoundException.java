package com.imooc.ecommerce.exception;

/**
 * @Description: 封装商品分类品牌信息找不到的异常
 * @Author: yfk
 * @Date:  2022-06-19
 * @return: null
 **/
public class GoodsCategoryBrandNotFoundException extends RuntimeException{
    public GoodsCategoryBrandNotFoundException() {
        super();
    }

    public GoodsCategoryBrandNotFoundException(String message) {
        super(message);
    }
}
