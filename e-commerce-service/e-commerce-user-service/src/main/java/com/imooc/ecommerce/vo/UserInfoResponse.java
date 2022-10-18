package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

        private int id;

        private String password;

        private String mobile;

        private String nickName;

        private LocalDateTime birthday;

        private String gender;

        private int role;
}
