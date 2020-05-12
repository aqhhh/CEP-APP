package com.example.demo.security.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

@ConfigurationProperties(prefix = "cep.security.redis")
@Order(0)
@Data
public class AuthRedisProperties {

    private Integer database = 0;
    private String host;
    private Integer port;
    private String password;
}
