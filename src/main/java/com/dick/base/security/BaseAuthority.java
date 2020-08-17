package com.dick.base.security;

import org.springframework.security.core.GrantedAuthority;

public class BaseAuthority implements GrantedAuthority {

    private String authority;

    public BaseAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
