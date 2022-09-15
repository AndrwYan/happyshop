package com.imooc.ecommerce.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GoodsListVO {

    private Integer id;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private LocalDateTime deletedAt;

    private Boolean isDeleted;

    private Integer categoryId;

    private Integer brandsId;

    private Boolean onSale;

    private Boolean shipFree;

    private Boolean isNew;

    private Boolean isHot;

    private String name;

    private String goodsSn;

    private Integer clickNum;

    private Integer soldNum;

    private Integer favNum;

    private BigDecimal marketPrice;

    private BigDecimal shopPrice;

    private String goodsBrief;

    private String images;

    private String descImages;

    private String goodsFrontImage;

    private String category_id;

    private String categoryName;

    private Integer brandId;

    private String logo;

    private String brandName;
}
