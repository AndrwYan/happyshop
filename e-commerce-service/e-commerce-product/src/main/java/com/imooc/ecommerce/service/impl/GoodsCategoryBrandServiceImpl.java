package com.imooc.ecommerce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.ecommerce.entity.GoodsCategoryBrand;
import com.imooc.ecommerce.mapper.GoodsCategoryBrandMapper;
import com.imooc.ecommerce.param.BrandFilterRequest;
import com.imooc.ecommerce.service.IGoodsCategoryBrandService;
import com.imooc.ecommerce.vo.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Service
public class GoodsCategoryBrandServiceImpl extends
        ServiceImpl<GoodsCategoryBrandMapper, GoodsCategoryBrand>
        implements IGoodsCategoryBrandService {

    @Resource
    private GoodsCategoryBrandMapper categoryBrandMapper;

    /**
     * @Description: 用于返回商品品牌分类列表信息
     * @Author: yfk
     * @Date: 2022-06-14
     * @return: com.imooc.ecommerce.vo.GoodCategoryBrandVO
     **/
    @Override
    public GoodsCategoryBrandListVO getGoodsCategoryBrand(BrandFilterRequest brandFilterRequest) {
        //分页插件
        PageHelper.startPage(brandFilterRequest.getPage(),brandFilterRequest.getPagePerNum());
        //分装分页参数
        List<GoodCategoryBrandVO> goodsCategoryBrandListVOS = categoryBrandMapper.listGoodsCategory();
        //需要进行类型转换,分页参数中有count
        Page listVOS = (Page) goodsCategoryBrandListVOS;
        return new GoodsCategoryBrandListVO(listVOS.getTotal(),goodsCategoryBrandListVOS);
    }

    /**
     * @Description: 通过商品分类(category)搜索该分类下的所有品牌(brand)
     * @Author: yfk
     * @Date: 2022-06-17
     * @param categoryInfoVO:
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @Override
    public BrandListVO GetCategoryBrandList(CategoryInfoVO categoryInfoVO) {
        List<BrandInfoVO> brandInfoVOS = categoryBrandMapper.listBrandByCategory();
        return  new BrandListVO((long) brandInfoVOS.size(),brandInfoVOS);
    }
}
