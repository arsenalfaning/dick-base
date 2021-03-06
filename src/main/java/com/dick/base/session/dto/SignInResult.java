package com.dick.base.session.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignInResult {

    private Long id;
    private String username;
    private String token;
    private LocalDateTime signUpTime;
}
