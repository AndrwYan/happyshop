package com.imooc.ecommerce.transactional;


import cn.hutool.core.date.DateUtil;
import com.imooc.ecommerce.dao.EcommerceUserDao;
import com.imooc.ecommerce.entity.EcommerceUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;

/**
 * @Description: 校验事务失效的场景
 * @Author: yfk
 * @Date:
 * @return: null
 **/
@Slf4j
@Service
public class Transactionallose{

    @Resource
    private EcommerceUserDao ecommerceUserDao;

    /**
     * @Description: rollbackOn 属性设置错误导致回滚失败
     * @Author: yfk
     * @Date: 2022-07-2
     * @return: void
     **/
    @Transactional(rollbackOn = Exception.class)
    public void wrongRollbackOn() throws IOException {
        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername("AnMa");
        ecommerceUser.setPassword("1997811");
        ecommerceUser.setExtraInfo("11111");
        ecommerceUser.setCreateTime(DateUtil.parse("2022-07-22"));
        ecommerceUser.setUpdateTime(DateUtil.parse("2022-07-24"));
        ecommerceUserDao.save(ecommerceUser);
        throw new IOException("异常了");
    }
    /**
     * @Description: 在同一个类内的方法调用(背后的具体原因是因为只有事务这个方法在spring中被这个类以外的代码
     * 调用，才会被spring生成的代理对象去管理。)
     * @Author: yfk
     * @Date: 2022-7-02
     * @return: void
     **/
    public void wrongInnerCall() throws IOException {
        this.wrongRollbackOn();
    }


}
