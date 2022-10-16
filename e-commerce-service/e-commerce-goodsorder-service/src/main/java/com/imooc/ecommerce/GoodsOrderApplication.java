package com.imooc.ecommerce;

import com.imooc.ecommerce.conf.DataSourceProxyAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@MapperScan(basePackages = {"com.imooc.ecommerce.mapper"})
@Import(DataSourceProxyAutoConfiguration.class)
public class GoodsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsOrderApplication.class, args);

    }

}
