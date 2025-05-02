package com.sportcheckin.entity;

import lombok.Data;

/**
 * 运动类型实体类
 */
@Data
public class SportType {
    /**
     * 运动类型ID
     */
    private Long id;
    
    /**
     * 运动类型名称
     */
    private String name;
    
    /**
     * 运动单位
     */
    private String unit;
    
    /**
     * 积分比率（每单位运动量对应的积分）
     */
    private Double scoreRate;
    
    /**
     * 单位标签（显示用）
     */
    private String unitLabel;
}