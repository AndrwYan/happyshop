package com.imooc.ecommerce.exception;

/**
 * @Description: 库存不足
 * @Author: yfk
 * @Date:  2022-06-11
 **/
public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException() {
        super();
    }

    public InsufficientInventoryException(String message) {
        super(message);
    }
}
