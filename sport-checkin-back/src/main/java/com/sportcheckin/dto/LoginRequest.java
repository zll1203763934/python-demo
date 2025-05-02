package com.sportcheckin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信小程序登录请求DTO
 */
@Data
public class LoginRequest {
    /**
     * 微信登录临时凭证code
     */
    @NotBlank(message = "登录凭证不能为空")
    private String code;
    
    /**
     * 用户信息
     */
    private UserInfo userInfo;
    
    /**
     * 用户信息内部类
     */
    @Data
    public static class UserInfo {
        /**
         * 用户昵称
         */
        private String nickName;
        
        /**
         * 用户头像URL
         */
        private String avatarUrl;
        
        /**
         * 性别 0-未知 1-男 2-女
         */
        private Integer gender;
    }
}