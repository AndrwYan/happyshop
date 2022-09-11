package com.imooc.ecommerce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.aop2.IsTryAgain;
import com.imooc.ecommerce.dto.GoodsInvInfoDTO;
import com.imooc.ecommerce.dto.SellDetailDTO;
import com.imooc.ecommerce.dto.SellInfoDTO;
import com.imooc.ecommerce.entity.Inventory;
import com.imooc.ecommerce.entity.InventoryDeductionRecord;
import com.imooc.ecommerce.exception.GoodsNotFoundException;
import com.imooc.ecommerce.exception.TryAgainException;
import com.imooc.ecommerce.mapper.InventoryDeductionRecordMapper;
import com.imooc.ecommerce.mapper.InventoryMapper;
import com.imooc.ecommerce.service.InventoryInfoService;
import com.imooc.ecommerce.service.InventoryService;
import com.imooc.ecommerce.vo.GoodsInvInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/**
 * @author yfk
 * @since 2022-07-02
 */
@Slf4j
@Service
@Transactional
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    private final InventoryInfoService inventoryInfoService;

    private final InventoryMapper inventoryMapper;

    private final InventoryDeductionRecordMapper inventoryDeductionRecordMapper;

    public InventoryServiceImpl(InventoryInfoService inventoryInfoService, InventoryMapper inventoryMapper, InventoryDeductionRecordMapper inventoryDeductionRecordMapper) {
        this.inventoryInfoService = inventoryInfoService;
        this.inventoryMapper = inventoryMapper;
        this.inventoryDeductionRecordMapper = inventoryDeductionRecordMapper;
    }


    /**
     * @param goodsInvInfoVO:
     * @Description: 设置库存
     * @Author: yfk
     * @Date:
     * @return: void
     **/
    @Override
    public void setInv(GoodsInvInfoVO goodsInvInfoVO) {

        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        //直接调用继承IService的getOne方法。
        Inventory inventory = getOne(queryWrapper);
        if (inventory == null) {
            throw new GoodsNotFoundException("找不到该商品的库存信息!");
        }

        inventory.setId(goodsInvInfoVO.getGoodsId());
        inventory.setStocks(goodsInvInfoVO.getNumbers());

        updateById(inventory);
    }

    @Override
    @Transactional(rollbackFor = TryAgainException.class)
    @IsTryAgain
    public Object deductGoods(int goodsId,int number,String orderSn) throws TryAgainException {

            //1.此处必须调用单独的事务方法
            Inventory inventoryInfo = inventoryInfoService.getInventoryInfo(goodsId);

            if (inventoryInfo == null) {
                throw new GoodsNotFoundException("该商品的库存不存在!");
            }

            Integer newStock = inventoryInfo.getStocks() - number;
            Integer currentVersion = inventoryInfo.getVersion();
            Integer newVersion = inventoryInfo.getVersion() + 1;

            Inventory inventory1 = new Inventory();
            inventory1.setUpdateTime(LocalDateTime.now());
            inventory1.setStocks(newStock);
            inventory1.setVersion(newVersion);

            UpdateWrapper<Inventory> updateWrapper = new UpdateWrapper<>();

            //update inventory set stocks = ?,version =  oldVersion+ 1 where goods = ? and version = oldVersion
            updateWrapper.eq("goods", goodsId)
                         .eq("version", currentVersion);
            Integer result = inventoryInfoService.updateStock(inventory1, updateWrapper);

            if (result > 0) {
                //插入流水表,以便反查,幂等。
                InventoryDeductionRecord inventoryDeductionRecord = new InventoryDeductionRecord();
                inventoryDeductionRecord.setGoodsId(goodsId);
                inventoryDeductionRecord.setNumber(number);
                inventoryDeductionRecord.setOrderSn(orderSn);
                inventoryDeductionRecord.setCreateTime(LocalDateTime.now());
                inventoryDeductionRecordMapper.insert(inventoryDeductionRecord);
            } else {
                throw new TryAgainException("重试失败!");
            }
            return result;
    }

    /**
     * @param sellInfoDTO:
     * @Description: 归还库存
     * @Author: yfk
     * @Date: 2022-07-22
     * @return: void
     **/
    @Override
    public void reback(SellInfoDTO sellInfoDTO) {

        SellDetailDTO sellDetailDTO = new SellDetailDTO(sellInfoDTO.getOrderSn(), 1);
        for (GoodsInvInfoDTO vo : sellInfoDTO.getGoodsInvInfoDTOS()) {
            //1.校验其是否有库存
            QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
            QueryWrapper<Inventory> goods = queryWrapper.eq("goods", vo.getGoodsId());
            Inventory inventory = getOne(goods);

            if (inventory == null) {
                throw new GoodsNotFoundException("该商品的库存不存在!");
            }

            Integer newStock = inventory.getStocks() + vo.getNumber();
            inventory.setStocks(newStock);
            saveOrUpdate(inventory);
        }
    }

}
