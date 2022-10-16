package com.imooc.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoResponse {

        private Long id;

        private String password;

        private String mobile;

        private String nickName;

        private LocalDateTime birthday;

        private String gender;

        private int role;
}
