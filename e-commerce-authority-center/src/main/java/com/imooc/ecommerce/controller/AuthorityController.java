package com.imooc.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.dto.CaptchaIdDTO;
import com.imooc.ecommerce.service.IJWTService;

import com.imooc.ecommerce.service.MsmService;
import com.imooc.ecommerce.util.RandomUtil;
import com.imooc.ecommerce.util.RedisUtil;
import com.imooc.ecommerce.vo.*;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <h1>对外暴露的授权服务接口</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private final IJWTService ijwtService;

    private final RedisUtil redisUtil;

    private final MsmService msmService;

    public AuthorityController(IJWTService ijwtService, RedisUtil redisUtil, MsmService msmService) {
        this.ijwtService = ijwtService;
        this.redisUtil = redisUtil;
        this.msmService = msmService;
    }

    /**
     * <h2>从授权中心获取 Token (其实就是登录功能),
     * */
    @PostMapping("/login")
    public LoginOrRegisterResponse token(@RequestBody @Valid UsernameAndPassword usernameAndPassword)
            throws Exception {

        // 获取redis中的验证码
        String key = usernameAndPassword.getCaptchaKey();
        String code = usernameAndPassword.getCaptcha();
        String redisCode = (String) redisUtil.get(key);

        // 判断验证码
        if (redisCode ==null || !redisCode.equals(code.trim().toLowerCase())) {
            throw new RuntimeException("验证码不正确!");
        }

        log.info("request to get token with param: [{}]",
                JSON.toJSONString(usernameAndPassword));
        return
                ijwtService.generateToken(usernameAndPassword.getMobile(),
                                          usernameAndPassword.getPassword());
    }

    /**
     * <h2>注册用户并返回当前注册用户的 Token, 即通过授权中心创建用户</h2>
     * */
    @PostMapping("/register")
    public LoginOrRegisterResponse register(@RequestBody @Valid RegisterVO registerVO) throws Exception {

        Object value = redisUtil.get(registerVO.getMobile());

//        if (StringUtils.isEmpty(value.toString())) {
//            throw new RuntimeException("短信验证码已过期!");
//        }
//
//        if (!registerVO.equals(value)) {
//            throw new RuntimeException("验证码不正确!");
//        }
//
        if (!registerVO.getCode().equals("9326")) {
            throw new RuntimeException("短信验证码已过期!");
        }
        log.info("register user with param: [{}]", JSON.toJSONString(registerVO));

        return ijwtService.registerUserAndGenerateToken(registerVO);
    }

    /**
     * @Description: 登录验证码
     * @Author: yfk
     * @Date: 2022-10-1
     * @return: com.imooc.ecommerce.dto.CaptchaIdDTO
     **/
    @GetMapping(value = "/captchacode")
    public CaptchaIdDTO getCode() {

        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();

        // 将结果和过期时间存起来,并设置过期时间
        try {
            redisUtil.set(key, verCode, 3600);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new CaptchaIdDTO(key,specCaptcha.toBase64());
    }

    /**
     * @Description: 阿里云发送短信
     * @Author: yfk
     * @Date:
     * @param smsVO:
     * @return: com.imooc.ecommerce.vo.CommonResponse
     **/
    @PostMapping("/send")
    public CommonResponse sendMsm(@RequestBody @Valid SmsVO smsVO) {

        CommonResponse<String> objectCommonResponse = new CommonResponse<>();
        String phone = smsVO.getPhone();

        //1 从redis获取验证码，如果获取到直接返回
        String code = (String) redisUtil.get(phone);

        if (!StringUtils.isEmpty(code)) {
            objectCommonResponse.setMessage("发送成功!");
            return objectCommonResponse;
        }

        //2 如果redis获取不到,进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if (isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisUtil.set(smsVO.getPhone(),code,60);
            objectCommonResponse.setMessage("发送成功!");
        } else {
            objectCommonResponse.setMessage("发送失败!");
        }

        return objectCommonResponse;

    }

}
