package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.entity.Brands;
import com.imooc.ecommerce.param.BrandFilterRequest;
import com.imooc.ecommerce.vo.BrandListVO;

/**
 * @author yfk
 * @since 2022-05-28
 */
public interface IBrandsService extends IService<Brands> {

     BrandListVO getBrandList(BrandFilterRequest brandFilterRequest);
}
