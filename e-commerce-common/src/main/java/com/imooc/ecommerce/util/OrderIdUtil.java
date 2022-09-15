package com.imooc.ecommerce.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Mr.zhou
 * @Date 2021/3/8
 * @Description 订单工具类 用于生成订单流水号  时间戳 + 6随机码
 */
public class OrderIdUtil {
    /**
     * 获取YYYY-MM-DD HH:mm:ss:SS格式
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

        return sdfTime.format(new Date());
    }
    /**
     * 随机生成六位数随机码
     * @return
     */
    public static int getRandomNum(){

        return (int)(Math.random()*999999);
    }
    public static String getOrderId(){
        String s = getTime().replaceAll("[[\\s-:punct:]]", "");
        return s+getRandomNum();
    }

}




