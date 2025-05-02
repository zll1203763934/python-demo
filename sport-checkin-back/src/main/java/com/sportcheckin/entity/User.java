package com.sportcheckin.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 微信小程序openid
     */
    private String openid;
    
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
    
    /**
     * 用户状态 0-正常 1-禁用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}