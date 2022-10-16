package com.imooc.ecommerce.controller;

import cn.hutool.http.HttpStatus;
import com.imooc.ecommerce.Exception.ShopCartGoodsNotFoundException;
import com.imooc.ecommerce.entity.OrderShoppingCart;
import com.imooc.ecommerce.request.CartItemRequest;
import com.imooc.ecommerce.request.UserInfoRequest;
import com.imooc.ecommerce.service.IOrderShoppingCartService;
import com.imooc.ecommerce.vo.CommonListResponse;
import com.imooc.ecommerce.vo.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yfk
 * @since 2022-08-14
 */
@Api
@RestController
@RequestMapping("/orderGoods")
public class OrderGoodsController {

    @Resource
    private IOrderShoppingCartService iOrderShoppingCartService;

    /**
     * @Description: 获取用户的购物车列表
     * @Author: yfk
     * @Date: 2022-08-209
     * @return: com.imooc.ecommerce.vo.CommonResponse
     **/
    @ApiOperation(value = "获取用户的购物车列表")
    @GetMapping("/allCartItem")
    public CommonResponse getCartItemList(@RequestBody @Validated UserInfoRequest userInfoRequest) {

        CommonListResponse cartItemList = iOrderShoppingCartService.getCartItemList(userInfoRequest);
        return new CommonResponse(HttpStatus.HTTP_OK,"success",cartItemList);

    }

    /**
     * @Description: 添加商品到购物车
     * @Author: yfk
     * @Date: 2022-08-21
     * @param cartItemRequest:
     * @return: void
     **/
    @PostMapping("/creatShopCart")
    public void createShopCart(@RequestBody @Validated CartItemRequest cartItemRequest) {
        iOrderShoppingCartService.createShopCartItem(cartItemRequest);
    }

    /**
     * @Description: 修改购物车商品
     * @Author: yfk
     * @Date: 2022-08-21
     * @param cartItemRequest:
     * @return: void
     **/
    @PostMapping("/updateShopCart")
    public void updateShopCart(@RequestBody @Validated CartItemRequest cartItemRequest) {

        iOrderShoppingCartService.updateShopCartItem(cartItemRequest);

    }

    /**
     * @Description: 删除购物车商品
     * @Author: yfk
     * @Date: 2022-08-21
     * @param cartItemRequest:
     * @return: void
     **/
    @DeleteMapping("/deleteShopCart")
    public void deleteShopCart(@RequestBody @Validated CartItemRequest cartItemRequest) {

        OrderShoppingCart byId = iOrderShoppingCartService.getById(cartItemRequest.getId());
        if (byId == null) {
            throw new ShopCartGoodsNotFoundException("购物车记录不存在!");
        }

        iOrderShoppingCartService.removeById(cartItemRequest.getId());
    }

}
