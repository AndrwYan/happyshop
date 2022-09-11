package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.entity.Category;
import com.imooc.ecommerce.vo.CategoryVO;

import java.util.List;

/**
 * @author yfk
 * @since 2022-05-28
 */
public interface ICategoryService extends IService<Category> {
    /**
     * @Description: 获得商品分类层级树
     * @Author: yfk
     * @Date: 2022-06-10
     * @return: null
     **/
    List<CategoryVO> listWithTree();
}
