package com.example.demo.service.impl;

import com.example.demo.common.exception.UserNotFoundException;
import com.example.demo.security.constant.GlobalConstant;
import com.example.demo.security.service.JwtTokenService;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @ClassName TokenServiceImpl
 * @Author fast
 * @Date 2020/3/3 18:05
 * @Version 1.0
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String generateToken(Long userId) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(userId));
        if (userDetails == null) {
            throw new UserNotFoundException();
        }
        final String token = jwtTokenService.generateToken(userId, GlobalConstant.TOKEN_EXPIRE_IN);
        return token;
    }
}
