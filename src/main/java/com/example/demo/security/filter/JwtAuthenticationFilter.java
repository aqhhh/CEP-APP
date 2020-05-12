package com.example.demo.security.filter;


import com.example.demo.security.exception.NeedAuthenticationException;
import com.example.demo.security.model.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * AbstractAuthenticationProcessingFilter - 身份验证的入口 、 处理基于浏览器交互的HTTP验证请求
 * 处理所有HTTP Request和Response对象，并将其封装成AuthenticationMananger可以处理的Authentication
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String TOKEN_HEADER = "Authorization";

    private final AuthenticationFailureHandler failureHandler;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationFailureHandler failureHandler, RequestMatcher matcher) {
        /*
         * protected AbstractAuthenticationProcessingFilter(RequestMatcher)
         *
         * matcher返回true 拦截请求
         */
        super(matcher);
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String authHeader = request.getHeader(TOKEN_HEADER);

        if (StringUtils.isEmpty(authHeader)) {
            throw new NeedAuthenticationException("未登陆");
        }

        final String authToken = authHeader;

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
        /*
         * AuthenticationManager 为认证管理接口类，其定义了认证方法 authenticate()。
         * ProviderManager 为认证管理类，实现了接口 AuthenticationManager，
         * 并在认证方法 authenticate() 中将身份认证委托给具有认证资格的 AuthenticationProvider 进行身份认证
         *
         * 这里相当于跳到了 JwtAuthenticationProvider的authenticate方法
         */
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
