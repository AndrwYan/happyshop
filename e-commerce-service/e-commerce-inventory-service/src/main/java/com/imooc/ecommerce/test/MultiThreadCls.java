package com.imooc.ecommerce.test;

import com.imooc.ecommerce.dto.GoodsInvInfoDTO;
import com.imooc.ecommerce.dto.SellInfoDTO;
import com.imooc.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: junit 不支持高并发测试，会提前退出，导致无法完成测试。
 * @Author: yfk
 * @Date: 2022-7-30
 **/

@Component
public class MultiThreadCls {

    @Resource
    private InventoryService inventoryService;

    public void func() {
        System.out.println(" start");

        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 默认为非守护
        //i就是需要扣减的库存
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                System.out.println("in tread:" + finalI);


            });
        }

        threadPool.shutdown();
        System.out.println("func end");
    }

}
