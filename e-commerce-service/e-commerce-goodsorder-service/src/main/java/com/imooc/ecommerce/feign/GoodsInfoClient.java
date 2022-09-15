package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.goods.GoodsListVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@FeignClient(
        contextId = "GoodsInfoClient",
        value = "e-commerce-product-service"
)
public interface GoodsInfoClient {

    /**
     * 根据ids查询简单的商品信息
     * */
    @RequestMapping(
            value = "/ecommerce-product-service/goods/batchgoods",
            method = RequestMethod.GET
    )
    List<GoodsListVO> getSimpleGoodsInfoByTableId(List<Integer> ids);

}
