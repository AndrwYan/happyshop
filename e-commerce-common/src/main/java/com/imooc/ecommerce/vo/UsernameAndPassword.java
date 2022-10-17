package com.imooc.ecommerce.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * <h1>用户名和密码</h1>
 * */
@Data
public class UsernameAndPassword {

    /** 用户名 */
    @NotNull
    private String nickname;

    /** 电话号码 */
    @NotNull
    private String mobile;

    /** 密码 */
    @NotNull
    private String password;

    /** 验证码 */
    @NotNull
    private String captcha;

    /** 验证码存储在redis中的key值 */
    @NotNull
    private String captchaKey;

}
