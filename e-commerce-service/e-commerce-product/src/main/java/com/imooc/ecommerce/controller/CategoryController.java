package com.imooc.ecommerce.controller;

import cn.hutool.core.bean.BeanUtil;
import com.imooc.ecommerce.entity.Category;
import com.imooc.ecommerce.exception.GoodsCategoryNotFoundException;
import com.imooc.ecommerce.param.DeleteCategoryRequest;
import com.imooc.ecommerce.service.ICategoryService;
import com.imooc.ecommerce.vo.CategoryInfoVO;
import com.imooc.ecommerce.vo.CategoryListVO;
import com.imooc.ecommerce.vo.CategoryVO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private ICategoryService iCategoryService;

    /**
     * @Description: 获取商品分类，返回分类层级给前端调用
     * @Author: yfk
     * @Date: 2022-06-10
     * @return: List<CategoryVO>
     **/
    @GetMapping(value = "/categoryTree")
    public List<CategoryVO> getAllCategoryList(){
        List<CategoryVO> categoryVOS = iCategoryService.listWithTree();
        return categoryVOS;
    }

    /**
     * @Description: 通过CategoryListVO的id属性去获取这个商品分类下的子商品
     * @Author: yfk
     * @Date: 2022-06-10
     * @return: com.imooc.ecommerce.vo.CategoryListVO
     **/
    @PostMapping(value = "/subcategory")
    public List<CategoryVO> getSubCategory(@RequestBody @Validated CategoryListVO categoryListVO) {
        //获取整个商品分类的层级树
        List<CategoryVO> categoryVOS = iCategoryService.listWithTree();
        //通过id匹配子级别
        List<CategoryVO> subCategory = categoryVOS.stream().filter(categoryVO ->
                categoryVO.getId().equals(categoryListVO.getId())
        ).collect(Collectors.toList());

        return subCategory.isEmpty()?null:subCategory.get(0).getChildren();
    }

    /**
     * @Description: 创建商品分类
     * @Author: yfk
     * @Date: 2022-06-10
     **/
    @PostMapping(value = "/createcategory")
    public boolean CreateCategory(@RequestBody @Validated CategoryInfoVO categoryInfoVO) {
        //如果没有父类目
        if (categoryInfoVO.getLevel() == 1) {
            categoryInfoVO.setParentCategoryId(null);
        }
        Category category = new Category();
        BeanUtil.copyProperties(categoryInfoVO,category);
        return iCategoryService.save(category);
    }

    /**
     * @Description: 删除该商品分类
     * @Author: yfk
     * @Date: 2022-06-10
     **/
    @GetMapping(value = "/deletecategory")
    public boolean deleteCategory(@RequestBody @Validated DeleteCategoryRequest deleteCategoryRequest) {

        Category result = iCategoryService.getById(deleteCategoryRequest.getId());
        //如果没查到抛出商品分类不存在自定义异常
        if (result == null) {
            throw new GoodsCategoryNotFoundException("商品分类不存在");
        }
        return iCategoryService.removeById(result.getId());
    }

    /**
     * @Description: 更新该商品分类信息
     * @Author: yfk
     * @Date: 2022-06-10
     **/
    @PostMapping (value = "/updatecategory")
    public boolean updateCategory(@RequestBody @Validated CategoryInfoVO categoryInfoVO) {
        Category result = iCategoryService.getById(categoryInfoVO.getId());
        //如果没查到抛出商品分类不存在自定义异常
        if (result == null) {
            throw new GoodsCategoryNotFoundException("商品分类不存在");
        }
        Category category = new Category();
        BeanUtil.copyProperties(categoryInfoVO,category);
        return iCategoryService.save(category);
    }
}
