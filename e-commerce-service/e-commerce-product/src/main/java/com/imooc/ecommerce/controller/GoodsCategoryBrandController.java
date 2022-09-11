package com.imooc.ecommerce.controller;

import cn.hutool.core.bean.BeanUtil;
import com.imooc.ecommerce.entity.GoodsCategoryBrand;
import com.imooc.ecommerce.exception.BrandNotFoundException;
import com.imooc.ecommerce.exception.CategoryNotFoundException;
import com.imooc.ecommerce.exception.GoodsCategoryNotFoundException;
import com.imooc.ecommerce.param.BrandFilterRequest;
import com.imooc.ecommerce.service.IBrandsService;
import com.imooc.ecommerce.service.ICategoryService;
import com.imooc.ecommerce.service.IGoodsCategoryBrandService;
import com.imooc.ecommerce.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 商品品牌分类相关接口：商品品牌分类相关接口，品牌很多，如果不和商品分类产生关系会很多，通过商品分类过滤出品牌
 * @author yfk
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/goodsCategoryBrand")
public class GoodsCategoryBrandController {

    @Resource
    private IGoodsCategoryBrandService iGoodsCategoryBrandService;

    @Resource
    private IBrandsService iBrandsService;

    @Resource
    private ICategoryService iCategoryService;

    /**
     * @Description: 返回商品分类品牌信息列表
     * @Author: yfk
     * @Date:
     * @param brandFilterRequest:
     * @return: com.imooc.ecommerce.vo.GoodsCategoryBrandListVO
     **/
    @PostMapping(value = "/list")
    public GoodsCategoryBrandListVO listGoodsCategoryBrandListVO(@RequestBody @Validated BrandFilterRequest brandFilterRequest) {
        GoodsCategoryBrandListVO goodsCategoryBrand = iGoodsCategoryBrandService.getGoodsCategoryBrand(brandFilterRequest);
        return goodsCategoryBrand;
    }

    /**
     * @Description: 通过商品分类信息查询该分类下的所有品牌
     * @Author: yfk
     * @Date:  2022-06-17
     * @param categoryInfoVO:
     * @return: java.util.List<com.imooc.ecommerce.vo.BrandInfoVO>
     **/
    @PostMapping(value = "/listallbrand")
    public BrandListVO getCategoryBrandList(@RequestBody CategoryInfoVO categoryInfoVO) {
        BrandListVO brandListVO = iGoodsCategoryBrandService.GetCategoryBrandList(categoryInfoVO);
        return brandListVO;
    }

    /**
     * @Description: 创建商品分类品牌记录
     * @Author: yfk
     * @Date:  2022-06-19
     * @param goodsCategoryBrandVO:
     * @return: void
     **/
    @PostMapping(value = "/createbrand")
    public void createCategoryBrand(@RequestBody @Validated GoodsCategoryBrandVO goodsCategoryBrandVO) {

        if ( null == iBrandsService.getById(goodsCategoryBrandVO.getBrandsId()) ) {
            throw new BrandNotFoundException("找不到该品牌!");
        }

        if ( null == iCategoryService.getById(goodsCategoryBrandVO.getCategoryId())) {
            throw new CategoryNotFoundException("找不到该商品分类!");
        }

        GoodsCategoryBrand goodsCategoryBrand = new GoodsCategoryBrand();
        BeanUtil.copyProperties(goodsCategoryBrandVO,goodsCategoryBrand);
        iGoodsCategoryBrandService.save(goodsCategoryBrand);
    }

    /**
     * @Description: 删除删除分类品牌信息
     * @Author: yfk
     * @Date:  2022-06-19
     * @param goodsCategoryBrandVO:
     * @return: void
     **/
    @PostMapping(value = "/delete")
    public void deleteCategoryBrand(@RequestBody @Validated GoodsCategoryBrandVO goodsCategoryBrandVO) {
        if (null == iGoodsCategoryBrandService.getById(goodsCategoryBrandVO.getId())) {
            throw new GoodsCategoryNotFoundException("该品牌分类不存在!");
        }
        iGoodsCategoryBrandService.removeById(goodsCategoryBrandVO.getId());
    }

    /**
     * @Description: 修改品牌信息
     * @Author: yfk
     * @Date: 2022-06-19
     * @param goodsCategoryBrandVO:
     * @return: void
     **/
    @PostMapping(value = "/update")
    public void updateCategoryBrand(@RequestBody @Validated GoodsCategoryBrandVO goodsCategoryBrandVO) {

        if (null == iGoodsCategoryBrandService.getById(goodsCategoryBrandVO.getId())) {
            throw new GoodsCategoryNotFoundException("该品牌分类不存在!");
        }

        if (null == iBrandsService.getById(goodsCategoryBrandVO.getId())) {
            throw new GoodsCategoryNotFoundException("该品牌不存在!");
        }

        if (null == iCategoryService.getById(goodsCategoryBrandVO.getId())) {
            throw new GoodsCategoryNotFoundException("该商品分类不存在!");
        }

        GoodsCategoryBrand goodsCategoryBrand = new GoodsCategoryBrand();
        BeanUtil.copyProperties(goodsCategoryBrandVO,goodsCategoryBrand);
        iGoodsCategoryBrandService.updateById(goodsCategoryBrand);
    }

}
