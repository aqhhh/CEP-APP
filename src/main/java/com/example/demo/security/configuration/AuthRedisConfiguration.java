package com.example.demo.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisShardInfo;

/*
 * redis配置
 */

@Configuration
@EnableConfigurationProperties({AuthRedisProperties.class})
public class AuthRedisConfiguration {

    @Autowired
    private AuthRedisProperties authRedisProperties;

    public JedisConnectionFactory jedisConnectionFactory() {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(authRedisProperties.getHost(), authRedisProperties.getPort());
        if (!StringUtils.isEmpty(authRedisProperties.getPassword())) {
            jedisShardInfo.setPassword(authRedisProperties.getPassword());
        }

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisShardInfo);
        jedisConnectionFactory.setDatabase(authRedisProperties.getDatabase());
        return jedisConnectionFactory;
    }

    @Bean(name = "authRedisTemplate")
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        return redisTemplate;
    }
}
