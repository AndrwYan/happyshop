package com.imooc.ecommerce.test;


import com.imooc.ecommerce.dto.SellInfoDTO;
import com.imooc.ecommerce.goods.GoodsInvInfoDTO;
import com.imooc.ecommerce.service.InventoryService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ComponentScan(basePackages = {"com.imooc.ecommerce.*"})
@MapperScan(basePackages = {"com.imooc.ecommerce.mapper"})

public class InventoryServiceTest {


    private static InventoryService inventoryService;

    public static void main(String[] args) {

        //1.获取inventoryService的bean对象
        SpringApplication.run(InventoryServiceTest.class, args);
        ApplicationContext context = SpringUtil.getApplicationContext();
        inventoryService = context.getBean(InventoryService.class);// 注意是Service，不是ServiceImpl

        CountDownLatch countDownLatch = new CountDownLatch(10);

        //i就是需要扣减的库存
        for (int i = 0; i < 10; i++) {

            SellInfoDTO sellInfoDTO = new SellInfoDTO();
            ArrayList<GoodsInvInfoDTO> objects = new ArrayList<>();
            GoodsInvInfoDTO goodsInvInfoDTO = new GoodsInvInfoDTO(1,10);
            objects.add(goodsInvInfoDTO);

            sellInfoDTO.setGoodsInvInfoDTOS(objects);
            sellInfoDTO.setOrderSn(String.valueOf(i));

            v1TestThread thread = new v1TestThread(i,countDownLatch,sellInfoDTO);
            thread.start();


        }
    }


    public static class v1TestThread extends Thread {

        private int num;

        private CountDownLatch countDownLatch;

        private SellInfoDTO sellInfoDTO;

        public v1TestThread(int num, CountDownLatch countDownLatch, SellInfoDTO sellInfoDTO) {
            this.num = num;
            this.countDownLatch = countDownLatch;
            this.sellInfoDTO = sellInfoDTO;
        }

        /**
         * @Description: 重写run方法
         * @Author: yfk
         * @Date: 2022-8-07
         * @return: void
         **/
        @Override
        public void run() {
            //2.每调用一次方法，倒计数就会减一。
            countDownLatch.countDown();

            System.out.println("扣减的商品orderSn:" + sellInfoDTO.getOrderSn() + "扣减的库存:" + sellInfoDTO.toString());

            try {
                //3.当倒计数＞0时，执行await方法会停止到当前。
                countDownLatch.await();
                String orderSn = sellInfoDTO.getOrderSn();

                List<GoodsInvInfoDTO> goodsInvInfoDTOS = sellInfoDTO.getGoodsInvInfoDTOS();

                for (GoodsInvInfoDTO vo:goodsInvInfoDTOS) {
                    inventoryService.deductGoods(vo.getGoodsId(),vo.getNumber(),orderSn);
                }

            } catch (Exception e) {
                //4.打印堆栈信息。
                e.printStackTrace();

            }
        }
    }

}

