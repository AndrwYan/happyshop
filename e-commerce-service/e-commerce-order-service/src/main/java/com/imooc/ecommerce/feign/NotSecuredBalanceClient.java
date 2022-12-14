package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h1>用户账户服务 Feign 接口</h1>
 * 不安全的
 * */
@FeignClient(
        contextId = "NotSecuredBalanceClient",
        value = "e-commerce-account-service"//你想要调用的微服务的账户id
)
public interface NotSecuredBalanceClient {

    @RequestMapping(
            value = "/ecommerce-account-service/balance/deduct-balance",
            method = RequestMethod.PUT
    )
    CommonResponse<BalanceInfo> deductBalance(@RequestBody BalanceInfo balanceInfo);
}
