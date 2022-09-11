package com.imooc.ecommerce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.entity.Banner;
import com.imooc.ecommerce.mapper.BannerMapper;
import com.imooc.ecommerce.service.IBannerService;
import org.springframework.stereotype.Service;

/**
 * @author yfk
 * @since 2022-05-29
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

}
