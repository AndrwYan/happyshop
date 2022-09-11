package com.imooc.ecommerce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yfk
 * @since 2022-09-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDeductionRecord extends Model<InventoryDeductionRecord> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单唯一编号
     */
    private String orderSn;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 扣减库存的流水记录插入时间
     */
    private LocalDateTime createTime;

    /**
     * 扣减的数量
     */
    private Integer number;


}
