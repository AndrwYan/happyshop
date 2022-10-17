package com.imooc.ecommerce.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.imooc.ecommerce.service.MsmService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(Map<String,Object> param,String phone) {

        //配置文件
        Config config = new Config()
                .setAccessKeyId("")
                .setAccessKeySecret("");
        config.endpoint = "dysmsapi.aliyuncs.com";

        try {

            Client client = new Client(config);
            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phone);
            request.setSignName("阿里云短信测试");
            request.setTemplateCode("SMS_154950909");
            request.setTemplateParam(JSONObject.toJSONString(param));

            client.sendSms(request);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
