package com.example.demo.security.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

@ConfigurationProperties(prefix = "cep.security")
@Order(0)
@Data
public class SecurityProperties {
    /**
     *不登陆放行页面 不需要token和权限 逗号分隔
     */
    private String[] withOutAuthorization;

    /**
     * 需要登陆但不需要权限的接口
     */
    private String[] withOutPermission;
}
