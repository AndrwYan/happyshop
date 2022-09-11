package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;


@Data
@AllArgsConstructor
public class GoodsCategoryBrandListVO {

    private Long total;

    private List<GoodCategoryBrandVO> goodsCategoryBrand;

}
