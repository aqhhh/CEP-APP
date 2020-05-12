package com.example.demo.security.exception;


import org.springframework.security.core.AuthenticationException;


public class NeedAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    /**
     * 创建一个新的实例 NeedAuthenticationException.
     *
     */
    public NeedAuthenticationException(String message) {
        super(message);
    }
}
