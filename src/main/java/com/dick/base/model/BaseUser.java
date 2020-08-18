package com.dick.base.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("base_user")
@Data
public class BaseUser {

    @TableId()
    private Long id;
    @TableField()
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;
    private LocalDateTime signUpTime;
    @JsonIgnore
    private String signInToken;

    private String roles;

    private String authorities;
}
