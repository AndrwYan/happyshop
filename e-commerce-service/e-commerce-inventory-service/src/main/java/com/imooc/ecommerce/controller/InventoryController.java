package com.imooc.ecommerce.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.ecommerce.dto.GoodsInvInfoDTO;
import com.imooc.ecommerce.dto.SellInfoDTO;
import com.imooc.ecommerce.entity.Inventory;
import com.imooc.ecommerce.exception.GoodsNotFoundException;
import com.imooc.ecommerce.exception.TryAgainException;
import com.imooc.ecommerce.service.InventoryService;
import com.imooc.ecommerce.vo.GoodsInvInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author yfk
 * @since 2022-07-02
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Resource
    private InventoryService inventoryService;

    /**
     * @Description: 设置商品的库存
     * @Author: yfk
     * @Date:  2022-7-2
     * @return: void
     **/
    @PostMapping ("/setinventory")
    public void setInv(@RequestBody @Validated GoodsInvInfoVO goodsInvInfoVO){

        inventoryService.setInv(goodsInvInfoVO);

    }

    /**
     * @Description: 得到库存详情
     * @Author: yfk
     * @Date:
     * @param goodsInvInfoVO:
     * @return: com.imooc.ecommerce.vo.GoodsInvInfoVO
     **/
    @GetMapping ("/getinventory")
    public GoodsInvInfoVO getInv(@RequestBody @Validated GoodsInvInfoVO goodsInvInfoVO) {

        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        //直接调用继承IService的getOne方法。
        Inventory inventory = inventoryService.getOne(queryWrapper);
        if (inventory == null) {
            throw new GoodsNotFoundException("找不到该商品的库存信息!");
        }
        return new GoodsInvInfoVO(inventory.getId(), inventory.getStocks());
    }

    /**
     * @Description: 扣减库存接口
     * @Author: yfk
     * @Date:  2022-07-22
     * @param sellInfoDTO:
     * @return: void
     **/
    @PutMapping(value = "/deductgoods")
    public void deductInventory(@RequestBody SellInfoDTO sellInfoDTO){

        String orderSn = sellInfoDTO.getOrderSn();
        List<GoodsInvInfoDTO> goodsInvInfoDTOS = sellInfoDTO.getGoodsInvInfoDTOS();

        goodsInvInfoDTOS.stream().map(
                vo -> {
                    try {
                        inventoryService.deductGoods(vo.getGoodsId(), vo.getNumber(), orderSn);
                    } catch (TryAgainException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );

    }
}
