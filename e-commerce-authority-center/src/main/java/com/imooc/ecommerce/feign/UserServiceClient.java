package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.dto.CreateUserDTO;
import com.imooc.ecommerce.dto.UserInfoResponse;
import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h1>
 *     用户账户服务 Feign 接口(安全的)
 * </h1>
 * */
@FeignClient(
        contextId = "UserServiceClient",
        value = "e-commerce-user-service"
)
public interface UserServiceClient {

    /**
     * <h2>调用用户服务创建用户接口</h2>
     * */
    @RequestMapping(
            value = "/ecommerce-user-service/ecommerceUser/createuser",
            method = RequestMethod.POST
    )
    CommonResponse createInfoByUserService(@RequestBody CreateUserDTO createUserDTO);
}
