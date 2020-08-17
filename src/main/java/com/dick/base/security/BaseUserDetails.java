package com.dick.base.security;

import com.dick.base.session.dto.SignInResult;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class BaseUserDetails implements UserDetails {

    private SignInResult signInResult;

    public BaseUserDetails(SignInResult signInResult) {
        this.signInResult = signInResult;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (signInResult.getAuthorities() != null) {
            signInResult.getAuthorities().forEach(auth -> authorities.add(new BaseAuthority(auth)));
        }
        if (signInResult.getRoles() != null) {
            signInResult.getRoles().forEach(auth -> authorities.add(new BaseRoleAuthority(auth)));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return signInResult.getUsername();
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

    public SignInResult getSignInResult() {
        return signInResult;
    }

}
