package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.entity.Inventory;

/**
 * @Description: 用数据库的乐观锁(CAS)实现扣减库存。扣减库存接口需要用到的接口,单独将此方法提取到该接口的原因
 *                  1.同一个bean内的方法单独事务不生效
 *                  2.因为要读取到最新的数据，数据库的隔离级别必须是RC(读已提交)
 * @Author: yfk
 * @Date: 2022-07-30
 **/
public interface InventoryInfoService extends IService<Inventory> {

    /**
     * @Description: 单独的事务查询
     * @Author: yfk
     * @Date: 2022-07-30
     * @param goodsId:
     * @return: com.imooc.ecommerce.entity.Inventory
     **/
    Inventory getInventoryInfo(int goodsId);

    /**
     * @Description: 扣减库存
     * @Author: yfk
     * @Date: 2022-9-7
     * @param inventory:
     * @param updateWrapper:
     * @return: java.lang.Integer
     **/
    Integer updateStock(Inventory inventory,UpdateWrapper updateWrapper);
}
