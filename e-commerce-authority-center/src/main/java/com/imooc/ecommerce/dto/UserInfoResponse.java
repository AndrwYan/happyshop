package com.imooc.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse implements Serializable {

        private Long id;
        private String password;
        private String mobile;
        private String nickName;
        private LocalDateTime birthday;
        private String gender;
        private int role;
}
