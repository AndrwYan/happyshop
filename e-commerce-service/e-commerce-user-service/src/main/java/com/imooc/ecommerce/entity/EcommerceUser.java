package com.imooc.ecommerce.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author yfk
 * @since 2022-05-21
 */
@TableName("t_ecommerce_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcommerceUser extends Model<EcommerceUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private LocalDateTime deletedAt;

    private Boolean isDeleted;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    private LocalDateTime birthday;

    /**
     * male表示男的
     */
    private String gender;

    /**
     * 1表示普通用户，2表示管理员
     */
    private Integer role;


}
