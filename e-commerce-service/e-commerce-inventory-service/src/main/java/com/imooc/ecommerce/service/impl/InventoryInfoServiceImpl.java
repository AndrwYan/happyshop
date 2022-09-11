package com.imooc.ecommerce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.imooc.ecommerce.entity.Inventory;

import com.imooc.ecommerce.mapper.InventoryMapper;
import com.imooc.ecommerce.service.InventoryInfoService;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yfk
 * @since 2022-07-02
 */
@Slf4j
@Service
public class InventoryInfoServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryInfoService {

    @Resource
    private InventoryMapper inventoryMapper;

    @Override
    public Inventory getInventoryInfo(int goodsId) {

        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods",goodsId);
        Inventory one = getOne(queryWrapper);

        return one;
    }

    @Override
    public Integer updateStock(Inventory inventory, UpdateWrapper updateWrapper) {
        int update = inventoryMapper.update(inventory, updateWrapper);
        return update;
    }

}
