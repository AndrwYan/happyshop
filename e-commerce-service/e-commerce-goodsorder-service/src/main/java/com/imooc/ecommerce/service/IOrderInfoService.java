package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.dto.OrderInfoDTO;
import com.imooc.ecommerce.dto.OrderInfoDetailDTO;
import com.imooc.ecommerce.entity.OrderInfo;
import com.imooc.ecommerce.request.OrderFilterRequest;
import com.imooc.ecommerce.request.OrderRequest;
import java.util.List;

/**
 * @author yfk
 * @since 2022-08-14
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    List<OrderInfoDTO> listOrderInfo(OrderFilterRequest orderFilterRequest);

    OrderInfoDetailDTO getOrderDetail(OrderRequest orderRequest);

    OrderInfoDTO createOrderInfo(OrderRequest orderRequest);

}
