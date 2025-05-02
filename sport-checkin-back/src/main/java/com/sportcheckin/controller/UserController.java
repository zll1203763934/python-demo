package com.sportcheckin.controller;

import com.sportcheckin.dto.LoginRequest;
import com.sportcheckin.dto.LoginResponse;
import com.sportcheckin.dto.Result;
import com.sportcheckin.entity.User;
import com.sportcheckin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 微信小程序用户登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        return Result.success(response);
    }

    /**
     * 获取当前登录用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        return Result.success(user);
    }
}