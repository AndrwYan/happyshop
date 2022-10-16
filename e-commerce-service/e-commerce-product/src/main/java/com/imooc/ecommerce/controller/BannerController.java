package com.imooc.ecommerce.controller;

import cn.hutool.core.bean.BeanUtil;
import com.imooc.ecommerce.common.BasePageResponse;
import com.imooc.ecommerce.dto.BannerDTO;
import com.imooc.ecommerce.entity.Banner;
import com.imooc.ecommerce.exception.BannerNotFoundException;
import com.imooc.ecommerce.service.IBannerService;
import com.imooc.ecommerce.vo.BannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
/**
 * @author yfk
 * @since 2022-05-29
 */
@Api
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Resource
    private IBannerService iBannerService;

    /**
     * @Description: 获取轮播图
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @ApiOperation(value = "获取轮播图")
    @PostMapping(value = "/list")
    public BasePageResponse getBannerList() {
        List<Banner> list = iBannerService.list();
        BasePageResponse<Banner> basePageResponse = new BasePageResponse<>();
        basePageResponse.setTotalNum(list.size());
        basePageResponse.setContentList(list);
        return basePageResponse;
    }

    /**
     * @Description: 新建轮播图信息
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @ApiOperation(value = "新建轮播图信息")
    @PostMapping(value = "/create")
    public BannerVO createBanner(@RequestBody @Validated BannerDTO bannerDTO) {
        Banner banner = new Banner();
        BeanUtil.copyProperties(bannerDTO,banner);
        iBannerService.save(banner);
        BannerVO bannerVO = new BannerVO();
        BeanUtil.copyProperties(bannerDTO,bannerVO);
        return bannerVO;
    }

    /**
     * @Description: 更新轮播图信息
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @ApiOperation(value = "更新轮播图信息")
    @PostMapping(value = "/update")
    public void updateBanner(@RequestBody BannerDTO bannerDTO) {

        Banner object = iBannerService.getById(bannerDTO.getId());

        if (object == null) {
            throw new BannerNotFoundException("找不到该轮播图!");
        }

        Banner banner = new Banner();
        BeanUtil.copyProperties(bannerDTO,banner);
        iBannerService.updateById(banner);
    }

    /**
     * @Description: 删除轮播图通过id
     * @Author: yfk
     * @Date: 2022-05-28
     * @return: com.imooc.ecommerce.vo.BrandListVO
     **/
    @ApiOperation(value = "删除轮播图通过id")
    @DeleteMapping(value = "/{bannerid}")
    public boolean deleteBanner(@PathVariable int bannerId) {
        return iBannerService.removeById(bannerId);
    }
}
