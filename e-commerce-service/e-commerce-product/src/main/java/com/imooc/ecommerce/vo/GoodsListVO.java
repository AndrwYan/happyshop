package com.imooc.ecommerce.vo;

import com.imooc.ecommerce.entity.Goods;
import lombok.Data;

@Data
public class GoodsListVO extends Goods {

    private String category_id;

    private String categoryName;

    private Integer brandId;

    private String logo;

    private String brandName;
}
