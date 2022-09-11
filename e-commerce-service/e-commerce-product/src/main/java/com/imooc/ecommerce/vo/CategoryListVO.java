package com.imooc.ecommerce.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @Description: 封装用于获取商品子分类的vo
 * @Author: yfk
 * @Date: 2022-06-10
 **/
@Data
public class CategoryListVO {

    //商品分类id
    @NotBlank(message = "商品id")
    private int id;

    //商品所属层级
    @NotBlank(message = "商品所属层级")
    private int level;

}
