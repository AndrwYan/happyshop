package com.imooc.ecommerce.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yfk
 * @since 2022-05-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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

}
