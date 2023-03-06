package com.example.demo.modules.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String userName;
    private String password;
    private String captcha;
    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
