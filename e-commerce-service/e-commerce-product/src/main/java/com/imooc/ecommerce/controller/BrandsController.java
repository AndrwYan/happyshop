package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.entity.Brands;
import com.imooc.ecommerce.exception.BrandNotFoundException;
import com.imooc.ecommerce.param.BrandFilterRequest;
import com.imooc.ecommerce.service.IBrandsService;
import com.imooc.ecommerce.vo.BrandInfoVO;
import com.imooc.ecommerce.vo.BrandListVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author yfk
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/brands")
public class BrandsController {

    @Resource
    private IBrandsService iBrandsService;
    /**
     * @Description: 获取品牌轮播图
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @PostMapping(value = "/lsitbrand")
    public BrandListVO getBrandList(@RequestBody BrandFilterRequest brandFilterRequest) {

        BrandListVO brandList = iBrandsService.getBrandList(brandFilterRequest);

        return brandList;
    }

    /**
     * @Description: 新建品牌信息
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @PostMapping(value = "/create")
    public BrandInfoVO createBrand(@RequestBody BrandInfoVO brandInfoVO) {
        Brands brands = new Brands();
        brands.setName(brandInfoVO.getName());
        brands.setLogo(brandInfoVO.getLogo());
        iBrandsService.save(brands);
        return brandInfoVO;
    }
    /**
     * @Description: 更新品牌信息
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @PostMapping(value = "/update")
    public void updateBrand(@RequestBody BrandInfoVO brandInfoVO) {
        Brands object = iBrandsService.getById(brandInfoVO.getId());

        if (object == null) {
            throw new BrandNotFoundException("找不到该品牌!");
        }
        Brands brands = new Brands();
        brands.setName(brandInfoVO.getName());
        brands.setLogo(brandInfoVO.getLogo());
        iBrandsService.updateById(brands);
    }

    /**
     * @Description: 删除品牌信息
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @DeleteMapping(value = "/{brandId}")
    public boolean deleteBrand(@PathVariable int brandId) {
        Brands brands = new Brands();

        return iBrandsService.removeById(brands);
    }
}
