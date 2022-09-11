package com.imooc.ecommerce.exception;

import com.baomidou.mybatisplus.extension.api.R;

/**
 * @Description: 失败重试异常
 * @Author: yfk
 * @Date: 2022-9-7
 **/
public class TryAgainException extends RuntimeException {

    public TryAgainException(String message) {
        super(message);
    }

}
