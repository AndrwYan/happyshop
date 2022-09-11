package com.imooc.ecommerce.test;


import com.imooc.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Test1 {

    private static InventoryService inventoryService;

    /**
     * 并发测试重试机制
     *
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//
//        //1.获取inventoryService的bean对象
//        SpringApplication.run(InventoryServiceTest.class, args);
//        ApplicationContext context = SpringUtil.getApplicationContext();
//        inventoryService = context.getBean(InventoryService.class);// 注意是Service，不是ServiceImpl
//        test();
//    }

    public static void test() throws Exception {

        //100个请求
        int clientTotal = 100;
        // 同时并发执行的线程数
        int threadTotal = 20;

        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量，此处用于控制并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁，可实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    //执行此方法用于获取执行许可，当总计未释放的许可数不超过200时，
                    //允许通行，否则线程阻塞等待，直到获取到许可。
                    semaphore.acquire();
                    inventoryService.deductGoods(1,10,"1");
                    //释放许可
                    semaphore.release();
                } catch (Exception e) {
                    //log.error("exception", e);
                    e.printStackTrace();

                }
                //闭锁减一
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();//线程阻塞，直到闭锁值为0时，阻塞才释放，继续往下执行
        executorService.shutdown();
    }
}
