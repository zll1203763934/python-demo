package com.sportcheckin.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportcheckin.dto.LoginRequest;
import com.sportcheckin.dto.LoginResponse;
import com.sportcheckin.entity.User;
import com.sportcheckin.exception.BusinessException;
import com.sportcheckin.mapper.UserMapper;
import com.sportcheckin.service.UserService;
import com.sportcheckin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // TODO: 实现微信登录逻辑
        // 1. 调用微信API获取openid
        String openid = getOpenidFromWechat(loginRequest.getCode());
        if (openid == null) {
            throw new BusinessException("获取微信openid失败");
        }
        
        // 2. 根据openid查询用户
        User user = getUserByOpenid(openid);
        
        // 3. 用户不存在则创建新用户
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            if (loginRequest.getUserInfo() != null) {
                user.setNickName(loginRequest.getUserInfo().getNickName());
                user.setAvatarUrl(loginRequest.getUserInfo().getAvatarUrl());
                user.setGender(loginRequest.getUserInfo().getGender());
            } else {
                // 设置默认值
                user.setNickName("用户" + openid.substring(0, 6));
                user.setGender(0);
            }
            user.setStatus(0); // 正常状态
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            
            // 保存用户
            saveOrUpdateUser(user);
        } else if (loginRequest.getUserInfo() != null) {
            // 更新用户信息
            user.setNickName(loginRequest.getUserInfo().getNickName());
            user.setAvatarUrl(loginRequest.getUserInfo().getAvatarUrl());
            user.setGender(loginRequest.getUserInfo().getGender());
            user.setUpdateTime(LocalDateTime.now());
            
            // 更新用户
            saveOrUpdateUser(user);
        }
        
        // 4. 生成JWT token
        String token = jwtUtil.generateToken(user.getId());
        
        // 组装返回结果
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(user);
        
        return response;
    }
    
    /**
     * 从微信获取openid
     *
     * @param code 微信授权码
     * @return openid
     */
    private String getOpenidFromWechat(String code) {
        // 微信小程序配置信息
        String appId = "wx123456789abcdef"; // 替换为实际的小程序appId
        String appSecret = "abcdef123456789"; // 替换为实际的小程序appSecret
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        
        // 构建请求参数
        String requestUrl = url + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        
        // 发送HTTP请求获取openid
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
            String responseBody = response.getBody();
            
            // 解析响应JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            
            // 检查是否有错误
            if (rootNode.has("errcode") && rootNode.get("errcode").asInt() != 0) {
                String errMsg = rootNode.has("errmsg") ? rootNode.get("errmsg").asText() : "未知错误";
                throw new BusinessException("微信登录失败: " + errMsg);
            }
            
            // 获取openid
            if (rootNode.has("openid")) {
                return rootNode.get("openid").asText();
            } else {
                throw new BusinessException("微信登录失败: 未获取到openid");
            }
        } catch (Exception e) {
            throw new BusinessException("微信登录失败: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) {
        // 从数据库查询用户
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByOpenid(String openid) {
        // 从数据库查询用户
        return userMapper.selectByOpenid(openid);
    }

    @Override
    public User saveOrUpdateUser(User user) {
        // 设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        
        // 如果用户ID为空，则插入新用户
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        
        return user;
    }
}