package com.imooc.ecommerce.conf;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <h1>
 *     开启服务间的调用保护, 需要给 RestTemplate 做一些包装
 *     增强RestTemplate对象，并对它进行保护。构造RestTemplate对象，注入SpringBean工程
 * </h1>
 * */
@Slf4j
@Configuration
public class SentinelConfig {

    /**
     * <h2>包装 RestTemplate</h2>
     * */
    @Bean
    @SentinelRestTemplate(
            fallback = "handleFallback", fallbackClass = RestTemplateExceptionUtil.class,//兜底策略的方法
            blockHandler = "handleBlock", blockHandlerClass = RestTemplateExceptionUtil.class//流量限流之后的处理方法
    )
    public RestTemplate restTemplate() {
        return new RestTemplate();  // 可以对其做一些业务相关的配置，例如(调用的请求超时。)
    }
}
