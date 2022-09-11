package com.imooc.ecommerce.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.ecommerce.dto.GoodsFilterDTO;
import com.imooc.ecommerce.entity.Goods;
import com.imooc.ecommerce.vo.GoodsListVO;

import java.util.List;

/**
 * Mapper 接口
 * @author yfk
 * @since 2022-05-28
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsListVO> listGoods(GoodsFilterDTO goodsFilterDTO);
}
