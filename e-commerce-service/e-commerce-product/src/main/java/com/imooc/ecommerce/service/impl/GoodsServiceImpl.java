package com.imooc.ecommerce.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.ecommerce.common.BasePageResponse;
import com.imooc.ecommerce.dto.GoodsFilterDTO;
import com.imooc.ecommerce.entity.Category;
import com.imooc.ecommerce.entity.Goods;
import com.imooc.ecommerce.exception.GoodsCategoryNotFoundException;
import com.imooc.ecommerce.mapper.CategoryMapper;
import com.imooc.ecommerce.mapper.GoodsMapper;
import com.imooc.ecommerce.service.IGoodsService;
import com.imooc.ecommerce.vo.GoodsFilterVO;
import com.imooc.ecommerce.vo.GoodsListVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper,
        Goods> implements IGoodsService {

    private CategoryMapper categoryMapper;

    private final GoodsMapper goodsMapper;
    public GoodsServiceImpl(CategoryMapper categoryMapper, GoodsMapper goodsMapper) {
        this.categoryMapper = categoryMapper;
        this.goodsMapper = goodsMapper;
    }

    /**
     * @Description: 查询商品列表
     * @Author: yfk
     * @Date: 2022-06-26
     * @param goodsFilterVO:
     * @return: java.util.List<com.imooc.ecommerce.vo.GoodsListVO>
     **/
    @Override
    public BasePageResponse<GoodsListVO> getGoodsList(GoodsFilterVO goodsFilterVO) {
        //通过TopCategory去商品分类表查询
        Category category = categoryMapper.selectById(goodsFilterVO.getTopCategory());
        if (category == null) {
            throw new GoodsCategoryNotFoundException("该商品分类不存在!");
        }

        GoodsFilterDTO goodsFilterDTO = new GoodsFilterDTO();
        BeanUtil.copyProperties(goodsFilterVO,goodsFilterDTO);
        //查询出来的商品的分类的等级level,需要传递给对应的mapper做动态sql查询,根据level的不同,写入不同的子查询条件
        goodsFilterDTO.setTopCategory(category.getLevel());

        //分页操作
        PageHelper.startPage(goodsFilterVO.getPages(),goodsFilterVO.getPagePerNum());
        List<GoodsListVO> goodsListVO = goodsMapper.listGoods(goodsFilterDTO);
        Page goodsList = (Page)goodsListVO;

        BasePageResponse<GoodsListVO> basePageResponse = new BasePageResponse<>();
        basePageResponse.setTotalNum(goodsList.getTotal());
        basePageResponse.setContentList(goodsListVO);
        return  basePageResponse;
    }
}
