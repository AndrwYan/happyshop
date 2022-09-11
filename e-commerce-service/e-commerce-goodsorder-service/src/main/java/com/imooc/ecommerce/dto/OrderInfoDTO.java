package com.imooc.ecommerce.dto;



import java.time.LocalDateTime;

public class OrderInfoDTO {

    private Integer id;

    private LocalDateTime addTime;

    private Integer userId;

    private String orderSn;

    /**
     * alipay(支付宝),wechat(微信)
     */
    private String payType;

    /**
     * PAYING(待支付), TRADE_SUCCESS(成功)， TRADE_CLOSED(超时关闭), WAIT_BUYER_PAY(交易创建), TRADE_FINISHED(交易结束)
     */
    private String status;

    private String post;

    private Float total;

    private String address;

    private String name;

    private String mobile;

}
