package com.imooc.ecommerce.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Integer id;

    private Integer userId;

    private String address;

    private String name;

    private String mobile;

    private String post;

}
