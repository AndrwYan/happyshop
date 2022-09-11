package com.imooc.ecommerce.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Data
public class GoodsCategoryBrandVO {

    private Integer id;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer brandsId;

}
