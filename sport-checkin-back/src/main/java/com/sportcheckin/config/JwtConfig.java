package com.sportcheckin.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT配置类
 */
@Component
public class JwtConfig {

    @Value("${jwt.secret:sportCheckinSecret}")
    private String secret;

    @Value("${jwt.expiration:604800}")
    private Long expiration;

    /**
     * 从token中获取用户ID
     *
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return JWT令牌
     */
    public String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userId.toString());
    }

    /**
     * 验证token是否有效
     *
     * @param token JWT令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取Claims
     *
     * @param token JWT令牌
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成token
     *
     * @param claims 声明
     * @param subject 主题（用户ID）
     * @return JWT令牌
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}