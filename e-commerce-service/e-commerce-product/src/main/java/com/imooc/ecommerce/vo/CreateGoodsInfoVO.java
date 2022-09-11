package com.imooc.ecommerce.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateGoodsInfoVO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    private Float marketPrice;

    private Float shopPrice;

    private String goodsBrief;

    private String images;

    private String descImages;

    private String goodsFrontImage;

}
