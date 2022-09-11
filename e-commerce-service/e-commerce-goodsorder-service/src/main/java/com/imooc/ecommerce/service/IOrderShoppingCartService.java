package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.dto.ShopCartInfoDTO;
import com.imooc.ecommerce.entity.OrderShoppingCart;
import com.imooc.ecommerce.request.CartItemRequest;
import com.imooc.ecommerce.request.UserInfoRequest;
import com.imooc.ecommerce.vo.CommonListResponse;

/**
 * @author yfk
 * @since 2022-08-14
 */
public interface IOrderShoppingCartService extends IService<OrderShoppingCart> {

    /**
     * @Description: 获取购物车列表
     * @Author: yfk
     * @Date: 2022-08-20
     * @param userInfoRequest:
     * @return: com.imooc.ecommerce.vo.CommonListResponse
     **/
    CommonListResponse getCartItemList(UserInfoRequest userInfoRequest);

    /**
     * @Description: 添加商品到购物车
     * @Author: yfk
     * @Date: 2022-08-21
     * @return: com.imooc.ecommerce.dto.ShopCartInfoDTO
     **/
    void createShopCartItem(CartItemRequest cartItemRequest);

    /**
     * @Description: 修改购物车的商品
     * @Author: yfk
     * @Date: 2022-08-21
     * @return: com.imooc.ecommerce.dto.ShopCartInfoDTO
     **/
    void updateShopCartItem(CartItemRequest cartItemRequest);

}
