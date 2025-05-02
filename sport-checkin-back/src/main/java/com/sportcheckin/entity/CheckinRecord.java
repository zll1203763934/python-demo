package com.sportcheckin.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 打卡记录实体类
 */
@Data
public class CheckinRecord {
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 运动类型ID
     */
    private Long sportTypeId;
    
    /**
     * 运动量
     */
    private Double amount;
    
    /**
     * 获得积分
     */
    private Integer score;
    
    /**
     * 打卡日期
     */
    private LocalDate checkinDate;
    
    /**
     * 打卡图片URL
     */
    private String imageUrl;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}