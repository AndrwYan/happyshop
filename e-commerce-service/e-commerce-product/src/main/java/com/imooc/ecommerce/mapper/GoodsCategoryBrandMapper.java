package com.imooc.ecommerce.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.ecommerce.entity.GoodsCategoryBrand;
import com.imooc.ecommerce.vo.BrandInfoVO;
import com.imooc.ecommerce.vo.GoodCategoryBrandVO;
import java.util.List;

/**
 * @author yfk
 * @since 2022-05-28
 */
public interface GoodsCategoryBrandMapper extends BaseMapper<GoodsCategoryBrand> {

    /**
     * @Description: 获取商品分类品牌信息列表
     * @Author: yfk
     * @Date: 2022-06-14
     * @return: java.util.List<com.imooc.ecommerce.vo.GoodsCategoryBrandListVO>
     **/
    public List<GoodCategoryBrandVO> listGoodsCategory();

    /**
     * @Description: 通过分类获取品牌信息
     * @Author: yfk
     * @Date: 2022-06-17
     * @return: java.util.List<com.imooc.ecommerce.vo.BrandInfoVO>
     **/
    public List<BrandInfoVO> listBrandByCategory();
}
