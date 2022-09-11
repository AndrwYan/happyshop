package com.imooc.ecommerce.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsFilterVO {
    @NotNull
    @ApiModelProperty("最小价格")
    private int priceMin;

    @NotNull
    @ApiModelProperty("最大价格")
    private int priceMax;

    @NotNull
    @ApiModelProperty("是否是热门商品")
    private boolean isHot;

    @NotNull
    @ApiModelProperty("是否是新")
    private boolean isNew;

    @NotNull
    @ApiModelProperty("是否是推荐")
    private boolean isTab;

    @NotNull
    @ApiModelProperty("父分类")
    private int topCategory;

    @NotNull
    @ApiModelProperty("页数")
    private int pages;

    @NotNull
    @ApiModelProperty("每页的数量")
    private int pagePerNum;

    @NotNull
    @ApiModelProperty("关键词")
    private String keyWords;

    @NotNull
    @ApiModelProperty("品牌编号")
    private int brand;
}
