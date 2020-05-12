package com.example.demo.security.security;

import com.example.demo.security.model.JwtAuthenticationToken;
import com.example.demo.security.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/*
 * 认证是由 AuthenticationManager 来管理的，但是真正进行认证的是 AuthenticationManager 中定义的 AuthenticationProvider
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();

        Long userId = jwtTokenService.getUserIdFromToken(token);

        if (userId == null) {
            logger.warn("token转化用户Id失败, token: {}", token);
            throw new UsernameNotFoundException("token转化用户Id失败");
        }

        /*
         * 这里从token获取到了userId
         * 因为JwtUserDetailsServiceImpl实现了UserDetailsService的loadUserByUsername方法
         * 所以会调用JwtUserDetailsServiceImpl.loadUserByUsername
         */
        UserDetails user = userDetailsService.loadUserByUsername(String.valueOf(userId));
        if (user == null) {
            logger.warn("用户不存在, userId: {}", userId);
            throw new UsernameNotFoundException("用户不存在");
        }

        //此处调用的方法开始涉及到redis
        if (!jwtTokenService.validateToken(token, user)) {
            logger.warn("token校验失败!, userId: {}", userId);
            throw new UsernameNotFoundException("token校验失败");
        }

        return new JwtAuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }


}
