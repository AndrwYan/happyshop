package com.imooc.ecommerce.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.entity.OrderGoods;
import com.imooc.ecommerce.mapper.OrderGoodsMapper;
import com.imooc.ecommerce.service.IOrderGoodsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yfk
 * @since 2022-08-14
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements IOrderGoodsService {

}
