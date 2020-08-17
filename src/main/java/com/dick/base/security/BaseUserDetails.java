package com.dick.base.security;

import com.dick.base.session.dto.UserBaseInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class BaseUserDetails implements UserDetails {

    private UserBaseInfo user;

    public BaseUserDetails(UserBaseInfo user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (user.getAuthorities() != null) {
            user.getAuthorities().forEach(auth -> authorities.add(new BaseAuthority(auth)));
        }
        if (user.getRoles() != null) {
            user.getRoles().forEach(auth -> authorities.add(new BaseRoleAuthority(auth)));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserBaseInfo getUser() {
        return user;
    }

}
