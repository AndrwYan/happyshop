package com.imooc.ecommerce.vo;

import lombok.Data;

@Data
public class GoodCategoryBrandVO {

    //商品分类品牌表id
    private Integer id;

    //品牌id
    private Integer brandId;

    //品牌的名字
    private String brandName;

    //品牌的logo
    private String logo;

    //每种商品分类的id
    private Integer categoryId;

    //商品分类的名字
    private String categoryName;

    //父级别分类id
    private Integer parentCategoryId;

    //属于哪个层级的
    private Integer level;

    //是不是首页轮播图
    private Boolean isTab;

}
