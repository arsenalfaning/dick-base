package com.dick.base.session.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignInParameter {

    @NotEmpty(message = "{username.notEmpty}")
    private String username;
    @NotEmpty(message = "{password.notEmpty}")
    private String password;
}
