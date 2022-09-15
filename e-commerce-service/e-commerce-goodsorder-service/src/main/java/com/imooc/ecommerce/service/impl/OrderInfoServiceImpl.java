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
import com.imooc.ecommerce.entity.OrderShoppingCart;
import com.imooc.ecommerce.feign.GoodsInfoClient;
import com.imooc.ecommerce.feign.SecuredInventoryClient;
import com.imooc.ecommerce.goods.GoodsInvInfoDTO;
import com.imooc.ecommerce.goods.GoodsListVO;
import com.imooc.ecommerce.inventory.SellInfoDTO;
import com.imooc.ecommerce.mapper.OrderGoodsMapper;
import com.imooc.ecommerce.mapper.OrderInfoMapper;
import com.imooc.ecommerce.mapper.OrderShoppingCartMapper;
import com.imooc.ecommerce.request.OrderFilterRequest;
import com.imooc.ecommerce.request.OrderRequest;
import com.imooc.ecommerce.service.IOrderInfoService;
import com.imooc.ecommerce.util.OrderIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yfk
 * @since 2022-08-14
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final OrderGoodsMapper orderGoodsMapper;

    private final GoodsInfoClient goodsInfoClient;

    private final OrderShoppingCartMapper orderShoppingCartMapper;

    private final SecuredInventoryClient securedInventoryClient;

    private final OrderInfoMapper orderInfoMapper;

    public OrderInfoServiceImpl(OrderGoodsMapper orderGoodsMapper, GoodsInfoClient goodsInfoClient, OrderShoppingCartMapper orderShoppingCartMapper, SecuredInventoryClient securedInventoryClient, OrderInfoMapper orderInfoMapper) {
        this.orderGoodsMapper = orderGoodsMapper;
        this.goodsInfoClient = goodsInfoClient;
        this.orderShoppingCartMapper = orderShoppingCartMapper;
        this.securedInventoryClient = securedInventoryClient;
        this.orderInfoMapper = orderInfoMapper;
    }

    /**
     * @param orderFilterRequest:
     * @Description: 获取订单列表
     * @Author: yfk
     * @Date: 2022-9-1
     * @return: com.imooc.ecommerce.dto.OrderInfoDTO
     **/
    @Override
    public List<OrderInfoDTO> listOrderInfo(OrderFilterRequest orderFilterRequest) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", orderFilterRequest.getUserId());

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
     * @param orderRequest:
     * @Description: 获取订单详情
     * @Author: yfk
     * @Date: 2022-9-6
     * @return: com.imooc.ecommerce.dto.OrderInfoDetailDTO
     **/
    @Override
    public OrderInfoDetailDTO getOrderDetail(OrderRequest orderRequest) {

        //如果是后台管理系统服务,那么只传递order的id，如果是电商系统服务还需要一个用户的id
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderRequest.getId());
        if (orderRequest.getUserId() != null) {
            queryWrapper.eq("user_id", orderRequest.getUserId());
        }

        OrderInfo orderInfo = getOne(queryWrapper);

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orderInfo, orderInfoDTO);

        //1.数据库设计的时候做了冗余保存了订单中的商品信息，因为是微服务，如果没有保存下来订单中的商品信息又要进行服务间调用。
        QueryWrapper<OrderGoods> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("order", orderInfo.getId());
        List<OrderGoods> orderGoods = orderGoodsMapper.selectList(queryWrapper1);

        List<OrderItemDTO> collect = orderGoods.stream().map(
                vo -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    BeanUtils.copyProperties(vo, orderItemDTO);
                    return orderItemDTO;
                }
        ).collect(Collectors.toList());

        return new OrderInfoDetailDTO(orderInfoDTO, collect);
    }

    /**
     * @param orderRequest:
     * @Description: 创建订单
     * 1. 从购物车中获取到选中的商品
     * 2. 商品的价格自己查询 - 访问商品服务 (跨微服务)
     * 3. 库存的扣减 - 访问库存服务 (跨微服务)
     * 4. 订单的基本信息表 - 订单的商品信息表
     * 5. 从购物车中删除已购买的记录
     * @Author: yfk
     * @Date: 2022-9-11
     * @return: com.imooc.ecommerce.dto.OrderInfoDTO
     **/
    @Override
    public OrderInfoDTO createOrderInfo(OrderRequest orderRequest) {

        // 1. 从购物车中获取到选中的商品
        QueryWrapper<OrderShoppingCart> orderShoppingCartQueryWrapper = new QueryWrapper<>();
        orderShoppingCartQueryWrapper.eq("user_id", orderRequest.getUserId())
                .eq("checked", true);
        List<OrderShoppingCart> orderShoppingCarts = orderShoppingCartMapper.selectList(orderShoppingCartQueryWrapper);

        // 1.1 将商品的唯一id和数量组装成Map方便计算下面的逻辑计算商品总价
        Map<Integer, Integer> map = orderShoppingCarts.stream()
                .collect(Collectors.toMap(OrderShoppingCart::getGoods, OrderShoppingCart::getNums));
        // 1.2 得到购物车中的商品id集合
        List<Integer> ids = orderShoppingCarts.stream()
                .map(OrderShoppingCart::getGoods)
                .collect(Collectors.toList());
        // 2. 商品的价格自己查询 - 访问商品服务 (跨微服务)
        List<GoodsListVO> simpleGoodsInfoByTableId = goodsInfoClient.getSimpleGoodsInfoByTableId(ids);
        List<OrderGoods> orderGoodsList = new ArrayList<>();

        // 2.1 单价乘以数量
        BigDecimal orderMount = simpleGoodsInfoByTableId.stream().map(
                vo -> {
                    Integer num = map.get(vo.getId());
                    BigDecimal number = new BigDecimal(String.valueOf(num));
                    BigDecimal orderTotal = number.multiply(vo.getMarketPrice());

                    OrderGoods orderGoods = new OrderGoods();
                    orderGoods.setGoods(vo.getId());
                    orderGoods.setGoodsName(vo.getName());
                    orderGoods.setGoodsImage(vo.getImages());
                    orderGoods.setGoodsPrice(vo.getShopPrice());
                    orderGoods.setNums(map.get(vo.getId()));
                    orderGoodsList.add(orderGoods);
                    return orderTotal;
                }
        ).reduce(BigDecimal::add).get();

        // 3.扣减库存
        // 3.1 创建orderSn号,库存服务也需要插入扣减库存流水表信息,所以orderSn需要作为参数。
        String orderId = OrderIdUtil.getOrderId();

        List<GoodsInvInfoDTO> goodsInvInfoDTOList = orderShoppingCarts.stream().map(
                vo -> {
                    GoodsInvInfoDTO goodsInvInfoDTO = new GoodsInvInfoDTO(vo.getId(), vo.getNums());
                    return goodsInvInfoDTO;
                }
        ).collect(Collectors.toList());

        SellInfoDTO sellInfoDTO = new SellInfoDTO(orderId, goodsInvInfoDTOList);

        if (!securedInventoryClient.deductGoodsInventory(sellInfoDTO).getData()) {
            throw new RuntimeException("扣减库存失败!");
        }

        // 4.创建订单记录
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderMount(orderMount);
        orderInfo.setOrderSn(orderId);
        orderInfo.setAddress(orderRequest.getAddress());
        orderInfo.setSignerName(orderRequest.getName());
        orderInfo.setSingerMobile(orderRequest.getMobile());
        orderInfoMapper.insert(orderInfo);

        OrderInfo orderInfo1 = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>()
                .eq("order_sn", orderId));
        //5.插入订单id，并插入order_goods表中
        orderGoodsList.forEach(vo -> vo.setOrder(orderInfo1.getId()));

        orderGoodsList.forEach(vo -> orderGoodsMapper.insert(vo));

        List<Integer> orderShopCartIds = orderShoppingCarts.stream().map(OrderShoppingCart::getId).collect(Collectors.toList());

        //6.从购物车中删除已购买的记录
        orderShoppingCartMapper.deleteBatchIds(orderShopCartIds);

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orderInfo1, orderInfoDTO);

        return orderInfoDTO;
    }

}
