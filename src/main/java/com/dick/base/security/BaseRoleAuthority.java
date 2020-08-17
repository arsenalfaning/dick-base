package com.dick.base.security;

import org.springframework.security.core.GrantedAuthority;

public class BaseRoleAuthority implements GrantedAuthority {

    private String role;

    public BaseRoleAuthority(String role) {
        this.role = String.format("ROLE_%s", role);
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
