package com.imooc.ecommerce.vo;

import lombok.Data;

/**
 * <h1>用户名和密码</h1>
 * */
@Data
public class UsernameAndPassword {

    /** 用户名 */
    private String nickName;

    /** 电话号码 */
    private String mobile;

    /** 密码 */
    private String password;
}
