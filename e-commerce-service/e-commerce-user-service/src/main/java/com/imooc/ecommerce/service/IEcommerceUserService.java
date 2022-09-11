package com.imooc.ecommerce.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.dto.CreateUserDTO;
import com.imooc.ecommerce.entity.EcommerceUser;
import com.imooc.ecommerce.vo.UserInfoResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yfk
 * @since 2022-05-21
 */
public interface IEcommerceUserService extends IService<EcommerceUser> {
     UserInfoResponse createUser(CreateUserDTO createUserReq);
}
