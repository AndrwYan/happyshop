package com.imooc.ecommerce.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodsFilterDTO {

    @ApiModelProperty("最小价格")
    private int priceMin;

    @ApiModelProperty("最大价格")
    private int priceMax;

    @ApiModelProperty("是否是热门商品")
    private boolean isHot;

    @ApiModelProperty("是否是新")
    private boolean isNew;

    @ApiModelProperty("是否是推荐")
    private boolean isTab;

    @ApiModelProperty("可能是父分类id")
    private int topCategory;

    @ApiModelProperty("页数")
    private int pages;

    @ApiModelProperty("每页的数量")
    private int pagePerNum;

    @ApiModelProperty("关键词")
    private String keyWords;

    @ApiModelProperty("品牌编号")
    private int brand;

    @ApiModelProperty("商品分类level")
    private int level;

}
