package com.sportcheckin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 运动打卡小程序后端服务主应用类
 */
@SpringBootApplication
@MapperScan("com.sportcheckin.mapper")
public class SportCheckinApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportCheckinApplication.class, args);
    }
}