package com.dick.base.session.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserBaseInfo {
    private Long id;
    private String username;
    private LocalDateTime signUpTime;

    private Set<String> roles;
    private Set<String> authorities;
}
