package com.imooc.ecommerce.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.dto.CreateUserDTO;
import com.imooc.ecommerce.entity.EcommerceUser;
import com.imooc.ecommerce.exception.BaseException;
import com.imooc.ecommerce.mapper.EcommerceUserMapper;
import com.imooc.ecommerce.service.IEcommerceUserService;
import com.imooc.ecommerce.util.SecurityUtils;
import com.imooc.ecommerce.vo.UserInfoResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
/**
 * @author yfk
 * @since 2022-05-21
 */
@Service
public class EcommerceUserServiceImpl extends ServiceImpl<EcommerceUserMapper, EcommerceUser> implements IEcommerceUserService {

    /**
     * @Description: 创建用户
     * @Author: yanfk
     * @Date:
     * @param createUserReq:
     * @return: com.imooc.ecommerce.vo.UserInfoResponse
     **/
    @Override
    public UserInfoResponse createUser(CreateUserDTO createUserReq) {

        List<EcommerceUser> list = new ArrayList<>();
        //通过电话号码查询
        if (StringUtils.isNotBlank(createUserReq.getMobile())) {
            QueryWrapper<EcommerceUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("mobile", createUserReq.getMobile());
            list = this.list(queryWrapper);
        }

        EcommerceUser user = list.get(0);
        if (user!=null) {
            throw new BaseException("该用户已经存在!");
        }

        EcommerceUser ecommerceUser = new EcommerceUser();
        BeanUtil.copyProperties(createUserReq,ecommerceUser);

        //对明文密码进行加密
        String encodePassword = SecurityUtils.encodePassword(createUserReq.getPassword());
        ecommerceUser.setPassword(encodePassword);

        if (!this.save(ecommerceUser)){
            throw new BaseException("创建用户失败");
        }

        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtil.copyProperties(createUserReq,userInfoResponse);

        return userInfoResponse;
    }
}
