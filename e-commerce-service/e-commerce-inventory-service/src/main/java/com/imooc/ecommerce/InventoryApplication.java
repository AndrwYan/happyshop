package com.imooc.ecommerce;

import com.imooc.ecommerce.conf.DataSourceProxyAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.imooc.ecommerce.mapper"})
@EnableRetry
@Import(DataSourceProxyAutoConfiguration.class)
public class InventoryApplication {

    public static void main(String[] args) {

        SpringApplication.run(InventoryApplication.class, args);

    }
}
