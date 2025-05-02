package com.sportcheckin.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 特殊情况申请实体类
 */
@Data
public class SpecialRequest {
    /**
     * 申请ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 申请类型 1-生理期 2-生病 3-其他
     */
    private Integer type;
    
    /**
     * 申请原因
     */
    private String reason;
    
    /**
     * 开始日期
     */
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    private LocalDate endDate;
    
    /**
     * 申请状态 0-待审核 1-已通过 2-已拒绝
     */
    private Integer status;
    
    /**
     * 审核备注
     */
    private String reviewRemark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}