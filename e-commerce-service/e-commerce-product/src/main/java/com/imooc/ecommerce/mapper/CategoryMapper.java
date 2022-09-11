package com.imooc.ecommerce.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.ecommerce.entity.Category;
import com.imooc.ecommerce.vo.CategoryBrandListVO;
import com.imooc.ecommerce.vo.GoodsCategoryBrandListVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yfk
 * @since 2022-05-28
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<GoodsCategoryBrandListVO> listCategoryBrand();

}
