package com.imooc.ecommerce.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.dto.OrderInfoDTO;
import com.imooc.ecommerce.dto.OrderInfoDetailDTO;
import com.imooc.ecommerce.dto.OrderItemDTO;
import com.imooc.ecommerce.entity.OrderGoods;
import com.imooc.ecommerce.entity.OrderInfo;
import com.imooc.ecommerce.mapper.OrderGoodsMapper;
import com.imooc.ecommerce.mapper.OrderInfoMapper;
import com.imooc.ecommerce.request.OrderFilterRequest;
import com.imooc.ecommerce.request.OrderRequest;
import com.imooc.ecommerce.service.IOrderInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yfk
 * @since 2022-08-14
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final OrderGoodsMapper orderGoodsMapper;

    public OrderInfoServiceImpl(OrderGoodsMapper orderGoodsMapper) {
        this.orderGoodsMapper = orderGoodsMapper;
    }

    /**
     * @Description: 获取订单列表
     * @Author: yfk
     * @Date: 2022-9-1
     * @param orderFilterRequest:
     * @return: com.imooc.ecommerce.dto.OrderInfoDTO
     **/
    @Override
    public List<OrderInfoDTO> listOrderInfo(OrderFilterRequest orderFilterRequest) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",orderFilterRequest.getUserId());

        Page<OrderInfo> orderInfoPage = new Page<>(orderFilterRequest.getPages(), orderFilterRequest.getPagePerNums());

        //调用继承自IService方法page()
        IPage<OrderInfo> page1 = page(orderInfoPage, queryWrapper);

        if (page1.getRecords() == null) {
            return null;
        }

        List<OrderInfoDTO> collect = page1.getRecords().stream().map(
                vo -> {
                    OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
                    BeanUtils.copyProperties(vo, orderInfoDTO);
                    return orderInfoDTO;
                }
        ).collect(Collectors.toList());

        return collect;
    }

    /**
     * @Description: 获取订单详情
     * @Author: yfk
     * @Date:  2022-9-6
     * @param orderRequest:
     * @return: com.imooc.ecommerce.dto.OrderInfoDetailDTO
     **/
    @Override
    public OrderInfoDetailDTO getOrderDetail(OrderRequest orderRequest) {

        //如果是后台管理系统服务,那么只传递order的id，如果是电商系统服务还需要一个用户的id
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",orderRequest.getId());
        if (orderRequest.getUserId() != null) {
            queryWrapper.eq("user_id",orderRequest.getUserId());
        }

        OrderInfo orderInfo = getOne(queryWrapper);

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orderInfo,orderInfoDTO);

        //1.数据库设计的时候做了冗余保存了订单中的商品信息，因为是微服务，如果没有保存下来订单中的商品信息又要进行服务间调用。
        QueryWrapper<OrderGoods> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("order",orderInfo.getId());
        List<OrderGoods> orderGoods = orderGoodsMapper.selectList(queryWrapper1);

        List<OrderItemDTO> collect = orderGoods.stream().map(
                vo -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    BeanUtils.copyProperties(vo,orderItemDTO);
                    return orderItemDTO;
                }
        ).collect(Collectors.toList());

        return new OrderInfoDetailDTO(orderInfoDTO,collect);
    }

}
