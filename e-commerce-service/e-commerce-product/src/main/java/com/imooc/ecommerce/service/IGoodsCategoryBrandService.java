package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.entity.GoodsCategoryBrand;
import com.imooc.ecommerce.param.BrandFilterRequest;
import com.imooc.ecommerce.vo.BrandListVO;
import com.imooc.ecommerce.vo.CategoryInfoVO;
import com.imooc.ecommerce.vo.GoodsCategoryBrandListVO;

/**
 * @author yfk
 * @since 2022-05-28
 */
public interface IGoodsCategoryBrandService extends IService<GoodsCategoryBrand> {

    /**
     * @Description: 获取商品分类品牌信息
     * @Author: yfk
     * @Date: 2022-06-17
     * @return: com.imooc.ecommerce.vo.GoodCategoryBrandVO
     **/
    GoodsCategoryBrandListVO getGoodsCategoryBrand(BrandFilterRequest brandFilterRequest);

    /**
     * @Description: 通过商品分类(category)搜索该分类下的所有品牌(brand)
     * @Author: yfk
     * @Date: 2022-06-17
     * @param categoryInfoVO:
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    BrandListVO GetCategoryBrandList(CategoryInfoVO categoryInfoVO);
}
