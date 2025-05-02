package com.sportcheckin.util;

import com.sportcheckin.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * JWT工具类
 */
@Component
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 生成JWT令牌
     *
     * @param userId 用户ID
     * @return JWT令牌
     */
    public String generateToken(Long userId) {
        return jwtConfig.generateToken(userId);
    }

    /**
     * 验证JWT令牌
     *
     * @param token JWT令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        return jwtConfig.validateToken(token);
    }

    /**
     * 从JWT令牌中获取用户ID
     *
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        return jwtConfig.getUserIdFromToken(token);
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            return (Long) authentication.getPrincipal();
        }
        return null;
    }
}