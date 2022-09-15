package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.inventory.SellInfoDTO;
import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <h1>
 *     安全的商品服务 Feign 接口
 *     设置了兜底的策略
 * </h1>
 * */
@FeignClient(
        contextId = "SecuredInventoryClient",
        value = "e-commerce-inventory-service"
)
public interface SecuredInventoryClient {

    /**
     * <h2>库存服务的扣减库存</h2>
     * */
    @RequestMapping(
            value = "/ecommerce-inventory-service/inventory/deductgoods",
            method = RequestMethod.POST
    )
    CommonResponse<Boolean> deductGoodsInventory(
            @RequestBody SellInfoDTO sellInfoDTO);
}
