package com.imooc.ecommerce.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.entity.Category;
import com.imooc.ecommerce.mapper.CategoryMapper;
import com.imooc.ecommerce.service.ICategoryService;
import com.imooc.ecommerce.vo.CategoryVO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements ICategoryService {

    /**
     * @Description: 用于展示商品分类的层级树
     * @Author: yfk
     * @Date: 2022-06-08
     * @return: com.imooc.ecommerce.vo.CategoryVO
     **/
    @Override
    public List<CategoryVO> listWithTree() {
        //调用查询所有的Category
        List<Category> categoryList = list();
        //转换成VO
        List<CategoryVO> categoryVOS = categoryList.stream().map(
                category -> {
                    CategoryVO categoryVO = new CategoryVO();
                    BeanUtil.copyProperties(category, categoryVO);
                    return categoryVO;
                }
        ).collect(Collectors.toList());

        //过滤出顶级父类,传入getChildrenData()递归方法
        List<CategoryVO> list = categoryVOS.stream().filter(categoryVO ->
                categoryVO.getParentCategoryId() == 0
        ).map((menu) -> {
            //调用递归函数,给CategoryVO对象的List<CategoryVO> children 属性赋值，
            //最后在map方法内部需要return 这个结果，否则调用collect()的时候，用的还是以前的
            menu.setChildren(getChildrenData(menu,categoryVOS));
            return menu;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * @Description: 递归获取子分类
     * @Author: yfk
     * @Date:  2022-06-27
     * @param root:
     * @param all:
     * @return: java.util.List<com.imooc.ecommerce.vo.CategoryVO>
     **/
    private List<CategoryVO> getChildrenData(CategoryVO root, List<CategoryVO> all) {

        List<CategoryVO> children = all.stream().filter(subjectVO ->
                subjectVO.getParentCategoryId().equals(root.getId())
        ).map(subjectVO -> {
            //递归调用
            subjectVO.setChildren(getChildrenData(subjectVO,all));
            return subjectVO;
        }).collect(Collectors.toList());

        return children;
    }
}
