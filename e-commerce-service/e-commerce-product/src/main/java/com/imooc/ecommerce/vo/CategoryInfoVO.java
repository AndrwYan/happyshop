package com.imooc.ecommerce.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Data
public class CategoryInfoVO {

    @NotBlank
    //分类id
    private Integer id;

    @NotBlank
    //商品的名字
    private String name;

    //父级别分类
    private Integer parentCategoryId;

    @NotBlank
    //属于哪个层级的
    private Integer level;

    @NotBlank
    //是不是首页轮播图
    private Boolean isTab;
}
