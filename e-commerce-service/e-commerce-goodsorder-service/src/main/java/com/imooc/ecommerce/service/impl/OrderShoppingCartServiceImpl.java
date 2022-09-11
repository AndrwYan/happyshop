package com.imooc.ecommerce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.Exception.ShopCartGoodsNotFoundException;
import com.imooc.ecommerce.dto.ShopCartInfoDTO;
import com.imooc.ecommerce.entity.OrderShoppingCart;
import com.imooc.ecommerce.mapper.OrderShoppingCartMapper;
import com.imooc.ecommerce.request.CartItemRequest;
import com.imooc.ecommerce.request.UserInfoRequest;
import com.imooc.ecommerce.service.IOrderShoppingCartService;
import com.imooc.ecommerce.vo.CommonListResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yfk
 * @since 2022-08-14
 */
@Service
public class OrderShoppingCartServiceImpl extends ServiceImpl<OrderShoppingCartMapper, OrderShoppingCart> implements IOrderShoppingCartService {

    /**
     * @param userInfoRequest:
     * @Description: 获取用户的购物车信息
     * @Author: yfk
     * @Date: 2022-08-20
     * @return: com.imooc.ecommerce.vo.CommonListResponse
     **/
    @Override
    public CommonListResponse getCartItemList(UserInfoRequest userInfoRequest) {

        QueryWrapper<OrderShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userInfoRequest.getUserId());
        List<OrderShoppingCart> list = list(queryWrapper);

        List<ShopCartInfoDTO> shopCartInfoDTOS = list.stream().map(
                item -> {
                    ShopCartInfoDTO shopCartInfoDTO = new ShopCartInfoDTO();
                    BeanUtils.copyProperties(item, shopCartInfoDTO);
                    return shopCartInfoDTO;
                }).collect(Collectors.toList());

        return new CommonListResponse( shopCartInfoDTOS.size(),shopCartInfoDTOS);
    }

    /**
     * @Description: 用户添加商品到购物车
     * @Author: yfk
     * @Date: 2022-08-21
     * @param cartItemRequest:
     * @return: com.imooc.ecommerce.dto.ShopCartInfoDTO
     **/
    @Override
    public void createShopCartItem(CartItemRequest cartItemRequest) {

        QueryWrapper<OrderShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartItemRequest.getUserId());
        queryWrapper.eq("goods_id", cartItemRequest.getGoodsId());

        OrderShoppingCart one = getOne(queryWrapper);

        if (one != null) {
            Integer number =  one.getNums() + cartItemRequest.getNums();
            one.setNums(number);
            updateById(one);
        } else {
            OrderShoppingCart orderShoppingCart = new OrderShoppingCart();
            BeanUtils.copyProperties(cartItemRequest,orderShoppingCart);
            save(orderShoppingCart);
        }

    }

    /**
     * @Description: 修改购物车的商品
     * @Author: yfk
     * @Date: 2022-08-21
     * @param cartItemRequest:
     * @return: void
     **/
    @Override
    public void updateShopCartItem(CartItemRequest cartItemRequest) {

        QueryWrapper<OrderShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartItemRequest.getUserId());
        queryWrapper.eq("goods_id", cartItemRequest.getGoodsId());

        OrderShoppingCart one = getOne(queryWrapper);

        if (one == null) {
            throw new ShopCartGoodsNotFoundException("购物车记录不存在!");
        } else {

            one.setNums(cartItemRequest.getNums() > 0?cartItemRequest.getNums():one.getNums());
            boolean isChecked = cartItemRequest.isChecked();
            one.setChecked(isChecked);
            updateById(one);
        }

    }
}
