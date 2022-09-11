package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBrandListVO {
    
    //品牌id
    private int brandId;
    //品牌的名字
    private String name;
    //品牌的logo
    private String logo;

    //分类id
    private int categoryId;
    //分类的名字
    private String categoryName;
    //分类的父级id
    private String parentCategory;
    private int level;
    private boolean isTable;


}
