package com.example.demo.security.service;

import com.example.demo.security.constant.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthRedisService {

    @Autowired
    @Qualifier("authRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value, int expire) {
        redisTemplate.boundValueOps(key)
                .set(value, expire, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public void flush(String key) {
        redisTemplate.delete(key);
    }

    public String getKey(String prefix, String str) {
        return new StringBuilder(prefix)
                .append(GlobalConstant.REDIS_AUTH_TOKEN_SPLIT)
                .append(str)
                .toString();
    }
}
