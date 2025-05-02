package com.sportcheckin.controller;

import com.sportcheckin.dto.Result;
import com.sportcheckin.entity.CheckinRecord;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 打卡记录控制器
 */
@RestController
@RequestMapping("/checkin")
public class CheckinRecordController {

    /**
     * 添加打卡记录
     *
     * @param record 打卡记录
     * @return 添加结果
     */
    @PostMapping
    public Result<CheckinRecord> addCheckinRecord(@RequestBody CheckinRecord record) {
        // TODO: 实现添加打卡记录逻辑
        // 1. 验证用户身份
        // 2. 检查是否已经打卡
        // 3. 计算积分
        // 4. 保存记录
        
        // 模拟添加成功
        record.setId(1L);
        record.setScore(calculateScore(record.getSportTypeId(), record.getAmount()));
        record.setCheckinDate(LocalDate.now());
        
        return Result.success(record);
    }

    /**
     * 获取用户打卡记录列表
     *
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 打卡记录列表
     */
    @GetMapping("/list")
    public Result<List<CheckinRecord>> getCheckinRecords(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        // TODO: 实现查询打卡记录逻辑
        // 模拟返回数据
        List<CheckinRecord> records = new ArrayList<>();
        CheckinRecord record = new CheckinRecord();
        record.setId(1L);
        record.setUserId(userId);
        record.setSportTypeId(1L);
        record.setAmount(5.0);
        record.setScore(50);
        record.setCheckinDate(LocalDate.now());
        record.setRemark("今天跑步很舒服");
        records.add(record);
        
        return Result.success(records);
    }

    /**
     * 获取用户本周打卡统计
     *
     * @param userId 用户ID
     * @return 本周打卡统计
     */
    @GetMapping("/weekly-stats")
    public Result<Object> getWeeklyStats(@RequestParam("userId") Long userId) {
        // TODO: 实现查询本周打卡统计逻辑
        // 模拟返回数据
        return Result.success(new Object() {
            public final int totalScore = 150;
            public final int targetScore = 180;
            public final int totalCheckins = 3;
        });
    }

    /**
     * 计算积分
     *
     * @param sportTypeId 运动类型ID
     * @param amount 运动量
     * @return 积分
     */
    private int calculateScore(Long sportTypeId, Double amount) {
        // TODO: 根据运动类型和运动量计算积分
        // 这里简单模拟计算逻辑
        int score = 0;
        switch (sportTypeId.intValue()) {
            case 1: // 跑步
                score = (int) (amount * 15);
                break;
            case 2: // 跳绳
                score = (int) (amount * 2);
                break;
            case 3: // 羽毛球
                score = (int) (amount * 70);
                break;
            case 4: // 游泳
                score = (int) (amount * 70);
                break;
            case 5: // 徒步
                score = (int) (amount * 0.0025);
                break;
            default:
                score = 0;
        }
        
        // 单日不足20分记为0分，单日上限70分
        if (score < 20) {
            score = 0;
        } else if (score > 70) {
            score = 70;
        }
        
        return score;
    }
}