package com.imooc.ecommerce.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.entity.Brands;
import com.imooc.ecommerce.mapper.BrandsMapper;
import com.imooc.ecommerce.param.BrandFilterRequest;
import com.imooc.ecommerce.service.IBrandsService;
import com.imooc.ecommerce.vo.BrandInfoVO;
import com.imooc.ecommerce.vo.BrandListVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Service
public class BrandsServiceImpl extends ServiceImpl<BrandsMapper, Brands> implements IBrandsService {

    @Override
    public BrandListVO getBrandList(BrandFilterRequest brandFilterRequest) {

        //构造分页对象
        Page<Brands> page = new Page<>(brandFilterRequest.getPage(), brandFilterRequest.getPagePerNum());

        //调用分页查询函数
        IPage<Brands> result = page(page);

        BrandListVO brandListVO = new BrandListVO();
        List<BrandInfoVO> brandInfoVOS = new ArrayList<>();
        brandListVO.setTotal(result.getTotal());

        for ( Brands vo:result.getRecords()) {
            BrandInfoVO infoVO = new BrandInfoVO();
            BeanUtil.copyProperties(vo,infoVO);
            brandInfoVOS.add(infoVO);
        }

        brandListVO.setBrandInfoVoList(brandInfoVOS);
        return brandListVO;
    }
}
