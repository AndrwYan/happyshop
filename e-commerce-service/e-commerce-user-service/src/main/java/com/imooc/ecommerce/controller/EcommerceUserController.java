package com.imooc.ecommerce.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.ecommerce.annotation.IgnoreResponseAdvice;
import com.imooc.ecommerce.dto.CreateUserDTO;
import com.imooc.ecommerce.entity.EcommerceUser;
import com.imooc.ecommerce.service.IEcommerceUserService;
import com.imooc.ecommerce.vo.BasePageResponse;
import com.imooc.ecommerce.vo.PageInfo;
import com.imooc.ecommerce.vo.UserInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.ObjectStreamField;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yfk
 * @since 2022-05-21
 */
@Api
@Slf4j
@RestController
@RequestMapping("/ecommerceUser")
public class EcommerceUserController {

    @Resource
    private IEcommerceUserService iUserService;

    /**
     * @Description: 获取用户列表(提供给其他服务)
     * @Author: yfk
     * @Date:
     * @param pageInfo: 分页信息
     * @return: com.imooc.ecommerce.vo.BasePageResponse
     **/
    @ApiOperation(value = "获取用户列表(提供给其他服务)",notes = "获取用户列表(提供给其他服务)",httpMethod = "POST")
    @PostMapping("/list")
    public BasePageResponse getUserList(@RequestBody PageInfo pageInfo) {

        BasePageResponse<EcommerceUser> pageResponse = new BasePageResponse<>();

        //构造分页对象
        Page<EcommerceUser> userPage = new Page<>(pageInfo.getPn(),pageInfo.getPSize());
        //使用BaseMapper中的无条件分页查询方法page();
        IPage<EcommerceUser> result = iUserService.page(userPage);

        pageResponse.setCurrentPage(result.getCurrent());
        pageResponse.setContentList(result.getRecords());
        pageResponse.setTotalPage(result.getTotal());
        pageResponse.setTotalNum(result.getTotal());

        return pageResponse;
    }

    /**
     * @Description: 通过手机号查询用户
     * @Author: yfk
     * @Date:
     * @param mobile: 手机号
     * @return: com.imooc.ecommerce.vo.BasePageResponse
     **/
    @ApiOperation(value = "通过手机号查询用户",httpMethod = "GET")
    @GetMapping("/userbymobile")
    public UserInfoResponse getUserByMobile(@RequestParam(value = "mobile") String mobile) {
        List<EcommerceUser> list = new ArrayList<>();

        if (StringUtils.isNotBlank(mobile)) {
            QueryWrapper<EcommerceUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("mobile", mobile);
            list = iUserService.list(queryWrapper);
        }

        EcommerceUser user = list.get(0);
        UserInfoResponse userInfoResponse = new UserInfoResponse();

        BeanUtils.copyProperties(user,userInfoResponse);

        return userInfoResponse;
    }

    /**
     * @Description: 通过Id查询用户
     * @Author: yfk
     * @Date:
     * @param id: 手机号
     * @return: com.imooc.ecommerce.vo.BasePageResponse
     **/
    @ApiOperation(value = "通过Id查询用户",httpMethod = "GET")
    @GetMapping("/userbyid")
    public UserInfoResponse getUserById(@RequestParam("id") String id) {

        UserInfoResponse userInfoResponse = new UserInfoResponse();
        if (StringUtils.isNotBlank(id)) {
            EcommerceUser user = iUserService.getById(id);
            BeanUtils.copyProperties(user,userInfoResponse);
        }
        return userInfoResponse;
    }

    /**
     * @Description: 创建用户(提供给其他服务)
     * @Author: yfk
     * @Date: 2022-10-12
     * @param createUserDTO: 分页信息
     * @return: com.imooc.ecommerce.vo.BasePageResponse
     **/
    @ApiOperation(value = "创建用户(提供给其他服务)",httpMethod = "POST")
    @PostMapping("/createuser")
    public UserInfoResponse saveUser(@RequestBody @Valid CreateUserDTO createUserDTO) {

        UserInfoResponse userInfo = new UserInfoResponse();
        if (createUserDTO != null) {
            userInfo = iUserService.createUser(createUserDTO);
        }
        return userInfo;
    }

}
