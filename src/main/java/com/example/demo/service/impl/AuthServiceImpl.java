package com.example.demo.service.impl;

import com.example.demo.security.service.AuthRedisService;
import com.example.demo.service.AuthService;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @ClassName AuthServiceImpl
 * @Author fast
 * @Date 2020/3/3 15:11
 * @Version 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthRedisService authRedisService;

    @Autowired
    private StringRedisTemplate redisTemplate;



    /**
     * 生成随机验证码
     */
    private String genRandomStr(int place) {
        StringBuilder stringBuilder = new StringBuilder();
        String repostory = "0123456789";
        for (int i = 0; i < place; i++) {
            stringBuilder.append(repostory.charAt((new Random()).nextInt(10)));
        }
        return stringBuilder.toString();
    }
}
