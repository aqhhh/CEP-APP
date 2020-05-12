package com.example.demo.security.model;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

public class MallGrantedAuthority implements GrantedAuthority, ConfigAttribute {

    public static final String AUTHORITY_SPLIT = ",";

    private final String url;
    private final String method;

    public MallGrantedAuthority(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }

    @Override
    public String getAuthority() {
        return this.url + AUTHORITY_SPLIT + this.method;
    }

    @Override
    public String getAttribute() {
        return String.valueOf(true);
    }

    @Override
    public String toString() {
        return "MallGrantedAuthority{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
