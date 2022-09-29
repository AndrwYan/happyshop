package com.imooc.ecommerce.filter.factory;

import com.imooc.ecommerce.filter.HeaderTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 创建HeaderTokenGatewayFilterFactory工厂对象
 * @Author: yfk
 * @Date: 2022-08-23
 * @return: null
 **/
@Component
public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    /**
     * @Description: 重写apply()方法，
     * @Author: yfk
     * @Date: 2022-08-23
     * @param config:
     * @return: org.springframework.cloud.gateway.filter.GatewayFilter
     **/
    @Override
    public GatewayFilter apply(Object config) {

        //初始化HeaderTokenGatewayFilter对象。
        return new HeaderTokenGatewayFilter();

    }

}
