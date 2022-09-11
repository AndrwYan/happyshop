package com.imooc.ecommerce.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserInfoResponse {

        private int id;

        private String password;

        private String mobile;

        private String nickName;

        private LocalDateTime birthday;

        private String gender;

        private int role;
}
