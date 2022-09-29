package com.imooc.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <h1>用户表实体类定义</h1>
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_user")
public class EcommerceUser implements Serializable {

    /** 自增主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** 用户名 */
    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    /** MD5 密码 */
    @Column(name = "password", nullable = false)
    private String password;

    /** 创建时间 */
    @CreatedDate
    @Column(name = "add_time")
    private LocalDateTime addTime;

    /** 更新时间 */
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**  */

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Boolean isDeleted;

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
