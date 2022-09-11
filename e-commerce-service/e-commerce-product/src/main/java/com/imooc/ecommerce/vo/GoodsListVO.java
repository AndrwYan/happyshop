package com.imooc.ecommerce.vo;

import com.imooc.ecommerce.entity.Goods;
import lombok.Data;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Data
public class GoodsListVO extends Goods {

    private String category_id;

    private String categoryName;

    private Integer brandId;

    private String logo;

    private String brandName;
}
