package com.imooc.ecommerce.dao;

import com.imooc.ecommerce.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>EcommerceUser Dao 接口定义</h1>
 * */
public interface EcommerceUserDao extends JpaRepository<EcommerceUser, Long> {

    /**
     * <h2>根据用户名查询 EcommerceUser 对象</h2>
     * select * from t_ecommerce_user where username = ?
     * */
    EcommerceUser findByNickName(String username);

    EcommerceUser findByMobile(String mobile);

}
