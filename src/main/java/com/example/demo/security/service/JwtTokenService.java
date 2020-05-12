package com.example.demo.security.service;

import com.example.demo.common.enums.AuthCacheKey;
import com.example.demo.security.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtTokenService {
    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String SECRET = "account&&wangyuqing";

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    @Autowired
    private AuthRedisService authRedisService;

    public Long getUserIdFromToken(String token) {
        Long userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            userId = claims.getSubject() == null ? 0L : Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            logger.error("从token提取userId时发生异常", e);
            userId = null;
        }
        return userId;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            logger.error("从token提取过期时间时发生异常", e);
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException eje) {
            logger.warn("token已过期", eje);
            claims = null;
        } catch (Exception e) {
            logger.error("从token提取claims时发生异常", e);
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(int expiration) {
        return new Date(System.currentTimeMillis() + Long.valueOf(expiration) * 1000L);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Long userId, int expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userId);
        String token = generateToken(claims, expiration);
        authRedisService
                .set(authRedisService.getKey(AuthCacheKey.BASE_TOKEN.getDesc(), String.valueOf(userId)), token, expiration);
        return token;
    }

    String generateToken(Map<String, Object> claims, int expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate(expiration))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String refreshToken(String token, int expiration) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateToken(claims, expiration);
            authRedisService
                    .set(authRedisService.getKey(AuthCacheKey.BASE_TOKEN.getDesc(), String.valueOf(claims.get(CLAIM_KEY_USERNAME))), token, expiration);
        } catch (Exception e) {
            logger.error("刷新token时发生异常", e);
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        UserModel user = (UserModel) userDetails;
        final Long userId = getUserIdFromToken(token);
        //通过userId从redis缓存里面取token
        final String validToken = authRedisService.get(authRedisService.getKey(AuthCacheKey.BASE_TOKEN.getDesc(), String.valueOf(userId)));
        if (!Objects.equals(token, validToken)) {
            logger.warn("token与redis里的不一致, 非法! userId:{}", userId);
            return false;
        }
        return (Objects.equals(userId, user.getId()))
                && !isTokenExpired(token);
    }
}
