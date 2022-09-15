package com.imooc.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yfk
 * @since 2022-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderGoods extends Model<OrderGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private LocalDateTime deletedAt;

    private Boolean isDeleted;

    /**
     * 订单id
     */
    private Integer order;

    /**
     * 商品id
     */
    private Integer goods;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsImage;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 购买的商品数量
     */
    private Integer nums;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
