package com.imooc.ecommerce.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Data
public class CategoryVO extends Model<CategoryVO> {

    //每种商品分类的id
    private Integer id;

    //商品分类的id
    private String name;

    //父级别分类
    private Integer parentCategoryId;

    //属于哪个层级的
    private Integer level;

    //是不是首页轮播图
    private Boolean isTab;

    //子级
    List<CategoryVO> children;

}
