package com.dick.base.session.dto;

import com.dick.base.util.DateUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SignInResult {

    private Long id;
    private String username;
    private LocalDateTime signUpTime;

    private Set<String> roles;
    private Set<String> authorities;

    public String getTimeDesc() {
        return DateUtil.format(signUpTime);
    }
}
