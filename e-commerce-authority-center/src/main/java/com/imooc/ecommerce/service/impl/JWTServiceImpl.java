package com.imooc.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.AuthorityConstant;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.dao.EcommerceUserDao;
import com.imooc.ecommerce.dto.CreateUserDTO;
import com.imooc.ecommerce.dto.UserInfoResponse;
import com.imooc.ecommerce.entity.EcommerceUser;
import com.imooc.ecommerce.feign.UserServiceClient;
import com.imooc.ecommerce.service.IJWTService;
import com.imooc.ecommerce.util.SecurityUtils;
import com.imooc.ecommerce.vo.LoginOrRegisterResponse;
import com.imooc.ecommerce.vo.LoginUserInfo;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * <h1>JWT 相关服务接口实现</h1>
 * */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JWTServiceImpl implements IJWTService {

    private final EcommerceUserDao ecommerceUserDao;

    private final UserServiceClient userServiceClient;

    public JWTServiceImpl(EcommerceUserDao ecommerceUserDao, UserServiceClient userServiceClient) {
        this.ecommerceUserDao = ecommerceUserDao;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public LoginOrRegisterResponse generateToken(String username, String password) throws Exception {

        return generateToken(username, password, 0);
    }

    @Override
    public LoginOrRegisterResponse generateToken(String username, String password, int expire)
            throws Exception {

        // 首先需要验证用户是否能够通过授权校验, 即输入的用户名和密码能否匹配数据表记录
        EcommerceUser ecommerceUser = ecommerceUserDao.findByNickName(
                username
        );

        if (null == ecommerceUser) {
            log.error("can not find user: [{}], [{}]", username, password);
            throw new RuntimeException("用户名或者密码不存在！");

        }

        if (!SecurityUtils.matchesPassword(password, ecommerceUser.getPassword())) {
            log.error("can not find user: [{}], [{}]", username, password);
            throw new RuntimeException("用户名或者密码不存在！");
        }

        // Token 中塞入对象, 即 JWT 中存储的信息, 后端拿到这些信息就可以知道是哪个用户在操作
        LoginUserInfo loginUserInfo = new LoginUserInfo(ecommerceUser.getId(), ecommerceUser.getNickName());

        return createToken(loginUserInfo, 0);
    }

    @Override
    public LoginOrRegisterResponse registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword)
            throws Exception {

        CreateUserDTO createUserDTO = new CreateUserDTO();
        BeanUtils.copyProperties(usernameAndPassword,createUserDTO);

        // 首先需要验证用户是否能够通过授权校验, 即输入的用户名和密码能否匹配数据表记录
        EcommerceUser ecommerceUser = ecommerceUserDao.findByNickName(
                usernameAndPassword.getNickname()
        );

        if (null != ecommerceUser) {
            log.error("the nickname have been register find user: [{}]", usernameAndPassword.getNickname());
            throw new RuntimeException("用户名或者密码不存在！");

        }

        //rpc接口调用创建用户的接口
        UserInfoResponse user = userServiceClient.createInfoByUserService(createUserDTO);

        if (null != user) {
            log.error("username is registered: [{}]", user.getNickName());
        }

        LoginUserInfo loginUserInfo = new LoginUserInfo(user.getId(),user.getNickName());
        LoginOrRegisterResponse userResponse = createToken(loginUserInfo, 0);
        return userResponse;
    }

    /**
     * @Description: 提取创建token的逻辑
     * @Author: yfk
     * @Date: 2022-10-1
     * @return: java.lang.String
     **/
    public LoginOrRegisterResponse createToken(LoginUserInfo loginUserInfo,int expire) throws Exception {

        if (expire <= 0) {
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }

        // 计算超时时间
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS)
                .atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        //构造token的过程。
        String token = Jwts.builder()
                // jwt body --> KV
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                // jwt id
                .setId(UUID.randomUUID().toString())
                // jwt 具体的过期时间
                .setExpiration(expireDate)
                // jwt 签名 --> 加密
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();

        return new LoginOrRegisterResponse(loginUserInfo.getId(),loginUserInfo.getNickname(),token,expireDate.toString());
    }

    /**
     * <h2>根据本地存储的私钥获取到 PrivateKey <对象/h2>
     * */
    private PrivateKey getPrivateKey() throws Exception {

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }
}
