# 运动打卡小程序后端服务

## 项目介绍

本项目是运动打卡小程序的后端服务，基于Spring Boot + MyBatis开发，提供用户管理、打卡记录、运动类型管理、积分计算和特殊情况处理等功能的API接口。

## 技术栈

- Java 8
- Spring Boot 2.7.5
- MyBatis 2.2.2
- MySQL 8.0
- JWT认证
- 微信小程序API集成

## 项目结构

```
sport-checkin-back
├── src/main/java/com/sportcheckin
│   ├── config        // 配置类
│   ├── controller    // 控制器
│   ├── entity        // 实体类
│   ├── dto           // 数据传输对象
│   ├── mapper        // MyBatis映射接口
│   ├── service       // 服务接口
│   │   └── impl      // 服务实现类
│   ├── util          // 工具类
│   └── SportCheckinApplication.java  // 启动类
├── src/main/resources
│   ├── mapper        // MyBatis XML映射文件
│   └── application.yml  // 应用配置文件
└── pom.xml           // Maven依赖配置
```

## 核心功能

1. 用户管理：注册、登录、个人信息管理
2. 打卡记录：添加、查询、统计打卡记录
3. 运动类型：管理不同运动类型及其积分规则
4. 积分计算：根据运动类型和运动量计算积分
5. 特殊情况处理：处理用户特殊情况申请

## 开发环境搭建

1. 克隆项目到本地
2. 创建MySQL数据库`sport_checkin`
3. 修改`application.yml`中的数据库连接信息
4. 配置微信小程序相关的环境变量
5. 使用Maven构建项目：`mvn clean install`
6. 运行项目：`mvn spring-boot:run`或直接运行`SportCheckinApplication`类

## API文档

启动项目后，可通过以下地址访问API文档：

- 接口基础路径：`http://localhost:8080/api`
- 主要接口：
  - 用户相关：`/user/**`
  - 打卡记录：`/checkin/**`
  - 运动类型：`/sport-type/**`
  - 特殊申请：`/special-request/**`