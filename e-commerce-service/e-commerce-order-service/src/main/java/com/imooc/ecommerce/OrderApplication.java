package com.imooc.ecommerce;

import com.imooc.ecommerce.conf.DataSourceProxyAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <h1>订单微服务启动入口</h1>
 * */
@EnableJpaAuditing
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient //服务注册
@Import(DataSourceProxyAutoConfiguration.class) //引入seata的代理数据源
public class OrderApplication {

    public static void main(String[] args) {
        // args是命令行传递的
        SpringApplication.run(OrderApplication.class, args);
    }
}
