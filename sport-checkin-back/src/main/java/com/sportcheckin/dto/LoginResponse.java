package com.sportcheckin.dto;

import com.sportcheckin.entity.User;
import lombok.Data;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponse {
    /**
     * 用户token
     */
    private String token;
    
    /**
     * 用户信息
     */
    private User user;
}