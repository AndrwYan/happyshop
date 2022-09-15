package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.common.BasePageResponse;
import com.imooc.ecommerce.entity.Goods;

import com.imooc.ecommerce.vo.GoodsFilterVO;
import com.imooc.ecommerce.vo.GoodsListVO;
import java.util.List;

/**
 * @author yfk
 * @since 2022-05-28
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * @Description: 获取商品列表
     * @Author: yfk
     * @Date: 2022-06-25
     * @return: java.util.List<com.imooc.ecommerce.vo.GoodsListVO>
     **/
    BasePageResponse<GoodsListVO> getGoodsList(GoodsFilterVO goodsFilterVO);
}
