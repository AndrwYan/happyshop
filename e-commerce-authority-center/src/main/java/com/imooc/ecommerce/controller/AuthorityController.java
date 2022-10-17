package com.imooc.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.dto.CaptchaIdDTO;
import com.imooc.ecommerce.service.IJWTService;

import com.imooc.ecommerce.util.RedisUtil;
import com.imooc.ecommerce.vo.LoginOrRegisterResponse;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    public AuthorityController(IJWTService ijwtService, RedisUtil redisUtil) {
        this.ijwtService = ijwtService;
        this.redisUtil = redisUtil;
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
    public LoginOrRegisterResponse register(@RequestBody UsernameAndPassword usernameAndPassword)
            throws Exception {

        log.info("register user with param: [{}]", JSON.toJSONString(
                usernameAndPassword
        ));

        return ijwtService.registerUserAndGenerateToken(usernameAndPassword);
    }

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

}
