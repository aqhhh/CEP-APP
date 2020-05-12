package com.example.demo.security.security;

import com.example.demo.common.base.BaseResponse;
import com.example.demo.common.enums.ResultCodeBase;
import com.example.demo.security.exception.NeedAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.warn("用户身份识别时发生错误! request:{}", request, authException);
        BaseResponse baseResponse = this.handleResponseAndStatus(authException, response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(objectMapper.writeValueAsString(baseResponse));
        response.getWriter().close();
    }

    private BaseResponse handleResponseAndStatus(AuthenticationException authException, HttpServletResponse response) {
        if (authException instanceof NeedAuthenticationException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new BaseResponse(ResultCodeBase.NOT_LOGIN);
        }
        if (authException instanceof UsernameNotFoundException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new BaseResponse(ResultCodeBase.INVALID_TOKEN);
        }
        if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new BaseResponse(ResultCodeBase.UNAUTHORIZED);
        }
        return BaseResponse.serverError();
    }
}
