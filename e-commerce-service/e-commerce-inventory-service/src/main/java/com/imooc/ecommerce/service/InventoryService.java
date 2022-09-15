package com.imooc.ecommerce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.dto.SellInfoDTO;
import com.imooc.ecommerce.entity.Inventory;
import com.imooc.ecommerce.exception.TryAgainException;
import com.imooc.ecommerce.vo.GoodsInvInfoVO;
/**
 * @author yfk
 * @since 2022-07-02
 */
public interface InventoryService extends IService<Inventory> {

    /**
     * @Description: 设置库存的接口
     * @Author: yfk
     * @Date:  2022-07-02
     * @param goodsInvInfoVO:
     * @return: void
     **/
    void setInv(GoodsInvInfoVO goodsInvInfoVO);

    /**
     * @Description: 基于数据库乐观锁扣减库存接口
     * @Author: yfk
     * @Date:  2022-07-22
     * @return: void
     **/
    Object deductGoods(int goodsId,int num,String orderSn) ;

    /**
     * @Description: 归还库存
     * @Author: yfk
     * @Date:  2022-7-23
     * @param sellInfoDTO:
     * @return: void
     **/
    void reback(SellInfoDTO sellInfoDTO);
}
