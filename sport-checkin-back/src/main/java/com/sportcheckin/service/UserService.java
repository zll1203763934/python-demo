package com.sportcheckin.service;

import com.sportcheckin.dto.LoginRequest;
import com.sportcheckin.dto.LoginResponse;
import com.sportcheckin.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 微信小程序用户登录
     *
     * @param loginRequest 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 根据openid获取用户信息
     *
     * @param openid 微信openid
     * @return 用户信息
     */
    User getUserByOpenid(String openid);

    /**
     * 保存或更新用户信息
     *
     * @param user 用户信息
     * @return 保存后的用户信息
     */
    User saveOrUpdateUser(User user);
}