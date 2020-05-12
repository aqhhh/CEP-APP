package com.example.demo.security.service.impl;

import com.example.demo.security.exception.NeedAuthenticationException;
import com.example.demo.security.model.UserModel;
import com.example.demo.security.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @ClassName AuthenticationImpl
 * @Description TODO
 * @Author fast
 * @Date 2020/3/3 11:20
 * @Version 1.0
 */
@Component
public class AuthenticationImpl implements Authentication, AuthenticationService {
    private UserModel getCurrentUser(){
        if (!isAuthenticated()) {
            throw new NeedAuthenticationException("未登陆");
        }
        return (UserModel) getPrincipal();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getCurrentUser().getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    @Override
    public Object getDetails() {
        return SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    @Override
    public Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())
                && authentication.getPrincipal() instanceof UserModel;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return getCurrentUser().getName();
    }

    @Override
    public Long getUserId() {
        return getCurrentUser().getId();
    }

    @Override
    public String getPhone() {
        return getCurrentUser().getPhone();
    }
}
