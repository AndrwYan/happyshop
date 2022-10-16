package com.imooc.ecommerce.controller;

import cn.hutool.core.bean.BeanUtil;
import com.imooc.ecommerce.common.BasePageResponse;
import com.imooc.ecommerce.entity.Brands;
import com.imooc.ecommerce.entity.Category;
import com.imooc.ecommerce.entity.Goods;
import com.imooc.ecommerce.exception.BrandNotFoundException;
import com.imooc.ecommerce.exception.CategoryNotFoundException;
import com.imooc.ecommerce.exception.GoodsNotFoundException;
import com.imooc.ecommerce.service.IBrandsService;
import com.imooc.ecommerce.service.ICategoryService;
import com.imooc.ecommerce.service.IGoodsService;
import com.imooc.ecommerce.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 1.关键词搜索
 *               2.通过价格区间筛选
 *               3.通过商品分类筛选
 *               4.查询热门商品
 *               5.查询新品
 *               6.商品的增删改没实现
 * @Author: yfk
 * @Date: 2022-8-11
 **/
@Api
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private IGoodsService iGoodsService;

    @Resource
    private IBrandsService iBrandsService;

    @Resource
    private ICategoryService iCategoryService;

    /**
     * @Description: 查询商品列表
     * @Author: yfk
     * @Date:  2022-06-25
     * @param goodsFilterVO:
     * @return: java.util.List<com.imooc.ecommerce.vo.GoodsListVO>
     **/
    @ApiOperation(value = "查询商品列表")
    @GetMapping(value = "/listall")
    public BasePageResponse<GoodsListVO> getGoodsList(@RequestBody @Validated GoodsFilterVO goodsFilterVO) {
        BasePageResponse<GoodsListVO> goodsList = iGoodsService.getGoodsList(goodsFilterVO);
        return goodsList;
    }

    /**
     * @Description: 现在用户提交订单有多个商品，你得批量查询商品的信息吧
     * @Author: yfk
     * @Date: 2022-9-11
     * @param ids:
     * @return: com.imooc.ecommerce.vo.BasePageResponse<com.imooc.ecommerce.vo.GoodsListVO>
     **/
    @ApiOperation(value = "现在用户提交订单有多个商品，你得批量查询商品的信息吧")
    @GetMapping(value = "/batchgoods")
    public List<GoodsListVO> batchGoodsList(List<String> ids) {

        //1.通过用户提交的多个订单id进行批量查询
        List<Goods> goods = (List<Goods>) iGoodsService.listByIds(ids);

        //2.迭代List集合,转换成VO
        final List<GoodsListVO> collect = goods.stream().map(
                vo -> {
                    GoodsListVO goodsListVO = new GoodsListVO();
                    BeanUtil.copyProperties(vo, goodsListVO);
                    return goodsListVO;
                }
        ).collect(Collectors.toList());

        return collect;
    }

    /**
     * @Description: 通过商品id获取商品信息
     * @Author: yfk
     * @Date:  2022-06-24
     * @param goodsInfoVO:
     * @return: com.imooc.ecommerce.vo.GoodsListVO
     **/
    @ApiOperation(value = "通过商品id获取商品信息")
    @GetMapping(value = "/getgoodsdetail")
    public GoodsListVO getGoodsDetail(@RequestBody @Validated GoodsInfoVO goodsInfoVO){
        Goods goods = iGoodsService.getById(goodsInfoVO.getId());
        if (goods == null) {
            throw new GoodsNotFoundException("该商品找不到!");
        }
        GoodsListVO goodsListVO = new GoodsListVO();
        BeanUtil.copyProperties(goods,goodsListVO);
        return goodsListVO;
    }
    
    /**
     * @Description: 创建品牌
     * @Author: yfk
     * @Date:  2022-06-27
     * @param createGoodsInfoVO:
     * @return: com.imooc.ecommerce.vo.GoodsInfoVO
     **/
    @ApiOperation(value = "创建品牌")
    @PostMapping(value = "/creategoods")
    public GoodsInfoVO createGoods(@RequestBody CreateGoodsInfoVO createGoodsInfoVO) {

        Category category = iCategoryService.getById(createGoodsInfoVO.getCategoryId());
        if (category == null) {
            throw new CategoryNotFoundException("该商品分类不存在!");
        }
        Brands brand = iBrandsService.getById(createGoodsInfoVO.getBrandsId());
        if (brand == null) {
            throw new BrandNotFoundException("该商品品牌不存在!");
        }
        Goods goods = new Goods();
        BeanUtil.copyProperties(createGoodsInfoVO,goods);
        iGoodsService.save(goods);
        return new GoodsInfoVO(goods.getId());
    }

    /**
     * @Description: 通过商品id删除商品
     * @Author: yfk
     * @Date:  2022-06-27
     * @param goodsId:
     * @return: com.imooc.ecommerce.vo.GoodsInfoVO
     **/
    @ApiOperation(value = "通过商品id删除商品")
    @DeleteMapping("/{goodsid}")
    public GoodsInfoVO deleteGoodsById(@PathVariable int goodsId) {
        iGoodsService.removeById(goodsId);
        return new GoodsInfoVO(goodsId);
    }

    /**
     * @Description: 更新操作
     * @Author: yfk
     * @Date:
     * @param createGoodsInfoVO:
     * @return: void
     **/
    @ApiOperation(value = "更新操作")
    @PostMapping("/updategoods")
    public void updateGoods(@RequestBody @Validated CreateGoodsInfoVO createGoodsInfoVO) {

        Goods object = iGoodsService.getById(createGoodsInfoVO.getId());
        if (object == null) {
            throw new GoodsNotFoundException("该商品不存在!");
        }

        Category category = iCategoryService.getById(createGoodsInfoVO.getCategoryId());
        if (category == null) {
            throw new CategoryNotFoundException("该商品分类不存在!");
        }

        Brands brand = iBrandsService.getById(createGoodsInfoVO.getBrandsId());
        if (brand == null) {
            throw new BrandNotFoundException("该商品品牌不存在!");
        }

        Goods goods = new Goods();
        BeanUtil.copyProperties(createGoodsInfoVO,goods);
        iGoodsService.updateById(goods);
    }
}

