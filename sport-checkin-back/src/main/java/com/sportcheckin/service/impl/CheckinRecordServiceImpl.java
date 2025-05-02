package com.sportcheckin.service.impl;

import com.sportcheckin.entity.CheckinRecord;
import com.sportcheckin.service.CheckinRecordService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

/**
 * 打卡记录服务实现类
 */
@Service
public class CheckinRecordServiceImpl implements CheckinRecordService {

    @Override
    public CheckinRecord addCheckinRecord(CheckinRecord record) {
        // TODO: 实现添加打卡记录逻辑
        // 1. 验证用户身份
        // 2. 检查是否已经打卡
        // 3. 计算积分
        // 4. 保存记录
        
        // 设置打卡日期和积分
        if (record.getCheckinDate() == null) {
            record.setCheckinDate(LocalDate.now());
        }
        
        // 计算积分
        int score = calculateScore(record.getSportTypeId(), record.getAmount());
        record.setScore(score);
        
        // 模拟保存记录并返回
        record.setId(1L); // 模拟ID生成
        return record;
    }

    @Override
    public List<CheckinRecord> getCheckinRecords(Long userId, LocalDate startDate, LocalDate endDate) {
        // TODO: 实现查询打卡记录逻辑
        // 根据用户ID和日期范围查询打卡记录
        
        // 模拟返回数据
        List<CheckinRecord> records = new ArrayList<>();
        
        // 如果没有指定日期范围，默认查询最近一周
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(6);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        // 模拟生成一些记录
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // 随机生成一些记录，这里简单模拟
            if (Math.random() > 0.3) { // 70%的概率有记录
                CheckinRecord record = new CheckinRecord();
                record.setId((long)(Math.random() * 1000));
                record.setUserId(userId);
                record.setSportTypeId(1L + (long)(Math.random() * 3)); // 1-3的运动类型
                record.setAmount(3.0 + Math.random() * 7); // 3-10公里
                record.setScore(calculateScore(record.getSportTypeId(), record.getAmount()));
                record.setCheckinDate(date);
                record.setRemark("打卡记录 " + date);
                records.add(record);
            }
        }
        
        return records;
    }

    @Override
    public Map<String, Object> getWeeklyStats(Long userId) {
        // TODO: 实现查询本周打卡统计逻辑
        
        // 获取本周的开始和结束日期
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        
        // 获取本周的打卡记录
        List<CheckinRecord> weeklyRecords = getCheckinRecords(userId, startOfWeek, endOfWeek);
        
        // 计算统计数据
        int totalScore = 0;
        int totalCheckins = weeklyRecords.size();
        Map<Integer, Boolean> checkinDays = new HashMap<>(); // 记录每天是否打卡
        
        for (CheckinRecord record : weeklyRecords) {
            totalScore += record.getScore();
            // 获取星期几 (1-7, 周一到周日)
            int dayOfWeek = record.getCheckinDate().get(WeekFields.of(Locale.CHINA).dayOfWeek());
            checkinDays.put(dayOfWeek, true);
        }
        
        // 目标积分和打卡天数
        int targetScore = 180; // 假设每周目标积分为180
        int targetDays = 5;   // 假设每周目标打卡天数为5天
        
        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("totalScore", totalScore);
        result.put("targetScore", targetScore);
        result.put("totalCheckins", totalCheckins);
        result.put("targetDays", targetDays);
        result.put("checkinDays", checkinDays);
        result.put("startDate", startOfWeek);
        result.put("endDate", endOfWeek);
        
        return result;
    }

    @Override
    public int calculateScore(Long sportTypeId, Double amount) {
        // 根据运动类型和运动量计算积分
        // 这里简单模拟计算逻辑
        int score = 0;
        int typeId = sportTypeId.intValue();
        
        switch (typeId) {
            case 1: // 跑步，每公里10分
                score = (int) (amount * 10);
                break;
            case 2: // 骑行，每公里5分
                score = (int) (amount * 5);
                break;
            case 3: // 游泳，每100米20分
                score = (int) (amount * 20);
                break;
            case 4: // 健身，每小时100分
                score = (int) (amount * 100);
                break;
            default: // 其他运动，每小时50分
                score = (int) (amount * 50);
                break;
        }
        
        return Math.max(score, 0); // 确保积分不为负
    }

    @Override
    public boolean hasCheckedInToday(Long userId, LocalDate date) {
        // TODO: 实现检查用户当天是否已打卡逻辑
        // 这里简单模拟，随机返回结果
        return Math.random() > 0.7; // 30%概率已打卡
    }

    @Override
    public Map<String, Object> getMonthlyStats(Long userId, int year, int month) {
        // TODO: 实现查询月度打卡统计逻辑
        
        // 获取指定月份的开始和结束日期
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        
        // 获取月度打卡记录
        List<CheckinRecord> monthlyRecords = getCheckinRecords(userId, startOfMonth, endOfMonth);
        
        // 计算统计数据
        int totalScore = 0;
        int totalCheckins = monthlyRecords.size();
        Map<Integer, Boolean> checkinDays = new HashMap<>(); // 记录每天是否打卡
        
        for (CheckinRecord record : monthlyRecords) {
            totalScore += record.getScore();
            int dayOfMonth = record.getCheckinDate().getDayOfMonth();
            checkinDays.put(dayOfMonth, true);
        }
        
        // 目标积分和打卡天数
        int targetScore = 720; // 假设每月目标积分为720
        int targetDays = 20;   // 假设每月目标打卡天数为20天
        
        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("totalScore", totalScore);
        result.put("targetScore", targetScore);
        result.put("totalCheckins", totalCheckins);
        result.put("targetDays", targetDays);
        result.put("checkinDays", checkinDays);
        result.put("startDate", startOfMonth);
        result.put("endDate", endOfMonth);
        
        return result;
    }
}