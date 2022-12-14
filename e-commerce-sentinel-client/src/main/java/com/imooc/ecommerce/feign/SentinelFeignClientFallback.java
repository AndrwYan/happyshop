package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <h1>Sentinel 对 OpenFeign 接口的降级策略</h1>
 * */
@Slf4j
@Component
public class SentinelFeignClientFallback implements SentinelFeignClient {//实现feign接口

    @Override
    public CommonResponse<String> getResultByFeign(Integer code) {

        log.error("request supply for test has some error: [{}]", code);
        return new CommonResponse<>(
                -1,
                "sentinel feign fallback",
                "input code: "+ code
        );
    }
}
