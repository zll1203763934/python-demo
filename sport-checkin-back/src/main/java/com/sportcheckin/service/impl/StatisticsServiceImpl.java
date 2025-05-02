package com.sportcheckin.service.impl;

import com.sportcheckin.entity.CheckinRecord;
import com.sportcheckin.entity.User;
import com.sportcheckin.service.CheckinRecordService;
import com.sportcheckin.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计服务实现类
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private CheckinRecordService checkinRecordService;


    @Override
    public Map<String, Object> getUserWeeklyStats(Long userId, LocalDate date) {
        // 如果未指定日期，使用当前日期
        if (date == null) {
            date = LocalDate.now();
        }
        
        // 获取指定日期所在周的开始和结束日期
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        
        // 获取用户在该周的打卡记录
        List<CheckinRecord> records = checkinRecordService.getCheckinRecords(userId, startOfWeek, endOfWeek);
        
        // 计算统计数据
        int totalScore = 0;
        int totalCheckins = records.size();
        Map<Integer, Boolean> checkinDays = new HashMap<>(); // 记录每天是否打卡
        Map<Long, Integer> sportTypeCount = new HashMap<>(); // 记录各运动类型的次数
        
        for (CheckinRecord record : records) {
            totalScore += record.getScore();
            // 获取星期几 (1-7, 周一到周日)
            int dayOfWeek = record.getCheckinDate().getDayOfWeek().getValue();
            checkinDays.put(dayOfWeek, true);
            
            // 统计运动类型
            Long sportTypeId = record.getSportTypeId();
            sportTypeCount.put(sportTypeId, sportTypeCount.getOrDefault(sportTypeId, 0) + 1);
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
        result.put("sportTypeCount", sportTypeCount);
        result.put("scoreProgress", Math.min(100, totalScore * 100 / targetScore)); // 积分完成百分比
        result.put("daysProgress", Math.min(100, totalCheckins * 100 / targetDays)); // 天数完成百分比
        
        return result;
    }

    @Override
    public Map<String, Object> getUserMonthlyStats(Long userId, int year, int month) {
        // 获取指定月份的开始和结束日期
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        
        // 获取用户在该月的打卡记录
        List<CheckinRecord> records = checkinRecordService.getCheckinRecords(userId, startOfMonth, endOfMonth);
        
        // 计算统计数据
        int totalScore = 0;
        int totalCheckins = 0;
        Map<Integer, Integer> dailyScores = new HashMap<>(); // 记录每天的积分
        Map<Long, Integer> sportTypeCount = new HashMap<>(); // 记录各运动类型的次数
        
        for (CheckinRecord record : records) {
            totalScore += record.getScore();
            int dayOfMonth = record.getCheckinDate().getDayOfMonth();
            dailyScores.put(dayOfMonth, dailyScores.getOrDefault(dayOfMonth, 0) + record.getScore());
            
            // 统计运动类型
            Long sportTypeId = record.getSportTypeId();
            sportTypeCount.put(sportTypeId, sportTypeCount.getOrDefault(sportTypeId, 0) + 1);
        }
        
        // 计算打卡天数（一天可能有多条记录）
        Set<Integer> checkinDays = dailyScores.keySet();
        totalCheckins = checkinDays.size();
        
        // 目标积分和打卡天数
        int targetScore = 720; // 假设每月目标积分为720
        int targetDays = 20;   // 假设每月目标打卡天数为20天
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth(); // 当月天数
        
        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("totalScore", totalScore);
        result.put("targetScore", targetScore);
        result.put("totalCheckins", totalCheckins);
        result.put("targetDays", targetDays);
        result.put("daysInMonth", daysInMonth);
        result.put("dailyScores", dailyScores);
        result.put("startDate", startOfMonth);
        result.put("endDate", endOfMonth);
        result.put("sportTypeCount", sportTypeCount);
        result.put("scoreProgress", Math.min(100, totalScore * 100 / targetScore)); // 积分完成百分比
        result.put("daysProgress", Math.min(100, totalCheckins * 100 / targetDays)); // 天数完成百分比
        
        return result;
    }

    @Override
    public Map<String, Object> getUserYearlyStats(Long userId, int year) {
        // 获取指定年份的开始和结束日期
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);
        
        // 获取用户在该年的打卡记录
        List<CheckinRecord> records = checkinRecordService.getCheckinRecords(userId, startOfYear, endOfYear);
        
        // 计算统计数据
        int totalScore = 0;
        Map<Integer, Integer> monthlyScores = new HashMap<>(); // 记录每月的积分
        Map<Integer, Integer> monthlyCheckins = new HashMap<>(); // 记录每月的打卡天数
        Map<Long, Integer> sportTypeCount = new HashMap<>(); // 记录各运动类型的次数
        
        for (CheckinRecord record : records) {
            totalScore += record.getScore();
            int month = record.getCheckinDate().getMonthValue();
            monthlyScores.put(month, monthlyScores.getOrDefault(month, 0) + record.getScore());
            
            // 统计运动类型
            Long sportTypeId = record.getSportTypeId();
            sportTypeCount.put(sportTypeId, sportTypeCount.getOrDefault(sportTypeId, 0) + 1);
        }
        
        // 计算每月打卡天数
        for (int month = 1; month <= 12; month++) {
            LocalDate startOfMonth = LocalDate.of(year, month, 1);
            LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
            
            // 筛选当月记录
            Set<LocalDate> uniqueDates = records.stream()
                    .filter(r -> !r.getCheckinDate().isBefore(startOfMonth) && !r.getCheckinDate().isAfter(endOfMonth))
                    .map(CheckinRecord::getCheckinDate)
                    .collect(Collectors.toSet());
            
            monthlyCheckins.put(month, uniqueDates.size());
        }
        
        // 目标积分
        int targetScore = 8640; // 假设每年目标积分为8640
        
        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("totalScore", totalScore);
        result.put("targetScore", targetScore);
        result.put("monthlyScores", monthlyScores);
        result.put("monthlyCheckins", monthlyCheckins);
        result.put("sportTypeCount", sportTypeCount);
        result.put("scoreProgress", Math.min(100, totalScore * 100 / targetScore)); // 积分完成百分比
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getWeeklyRanking(LocalDate date, int limit) {
        // 如果未指定日期，使用当前日期
        if (date == null) {
            date = LocalDate.now();
        }
        
        // 获取指定日期所在周的开始和结束日期
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        
        // 这里简单模拟一些排行数据
        List<Map<String, Object>> ranking = new ArrayList<>();
        
        // 模拟10个用户的排行数据
        int userCount = Math.min(10, limit);
        for (int i = 1; i <= userCount; i++) {
            Map<String, Object> userRank = new HashMap<>();
            User user = new User();
            user.setId((long) i);
            user.setNickName("用户" + i);
            user.setAvatarUrl("https://example.com/avatar" + i + ".png");
            
            userRank.put("user", user);
            userRank.put("score", 200 - i * 10); // 模拟积分，递减
            userRank.put("checkinDays", 7 - i % 3); // 模拟打卡天数
            userRank.put("rank", i); // 排名
            
            ranking.add(userRank);
        }
        
        return ranking;
    }

    @Override
    public List<Map<String, Object>> getMonthlyRanking(int year, int month, int limit) {
        // 获取指定月份的开始和结束日期
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        
        // TODO: 实现月排行榜逻辑
        // 这里简单模拟一些排行数据
        List<Map<String, Object>> ranking = new ArrayList<>();
        
        // 模拟10个用户的排行数据
        int userCount = Math.min(10, limit);
        for (int i = 1; i <= userCount; i++) {
            Map<String, Object> userRank = new HashMap<>();
            User user = new User();
            user.setId((long) i);
            user.setNickName("用户" + i);
            user.setAvatarUrl("https://example.com/avatar" + i + ".png");
            
            userRank.put("user", user);
            userRank.put("score", 800 - i * 30); // 模拟积分，递减
            userRank.put("checkinDays", 28 - i % 5); // 模拟打卡天数
            userRank.put("rank", i); // 排名
            
            ranking.add(userRank);
        }
        
        return ranking;
    }

    @Override
    public Map<String, Object> getUserCheckinCalendar(Long userId, int year, int month) {
        // 获取指定月份的开始和结束日期
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        
        // 获取用户在该月的打卡记录
        List<CheckinRecord> records = checkinRecordService.getCheckinRecords(userId, startOfMonth, endOfMonth);
        
        // 按日期分组，记录每天的打卡情况
        Map<Integer, List<CheckinRecord>> dailyRecords = new HashMap<>();
        for (CheckinRecord record : records) {
            int day = record.getCheckinDate().getDayOfMonth();
            if (!dailyRecords.containsKey(day)) {
                dailyRecords.put(day, new ArrayList<>());
            }
            dailyRecords.get(day).add(record);
        }
        
        // 计算每天的积分和运动类型
        Map<Integer, Integer> dailyScores = new HashMap<>();
        Map<Integer, List<Long>> dailySportTypes = new HashMap<>();
        
        for (Map.Entry<Integer, List<CheckinRecord>> entry : dailyRecords.entrySet()) {
            int day = entry.getKey();
            List<CheckinRecord> dayRecords = entry.getValue();
            
            // 计算当天总积分
            int dayScore = dayRecords.stream().mapToInt(CheckinRecord::getScore).sum();
            dailyScores.put(day, dayScore);
            
            // 记录当天运动类型
            List<Long> sportTypes = dayRecords.stream()
                    .map(CheckinRecord::getSportTypeId)
                    .distinct()
                    .collect(Collectors.toList());
            dailySportTypes.put(day, sportTypes);
        }
        
        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("year", year);
        result.put("month", month);
        result.put("daysInMonth", YearMonth.of(year, month).lengthOfMonth());
        result.put("checkinDays", dailyRecords.keySet());
        result.put("dailyScores", dailyScores);
        result.put("dailySportTypes", dailySportTypes);
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getUserSportTypeDistribution(Long userId, LocalDate startDate, LocalDate endDate) {
        // 如果未指定日期范围，默认查询最近一个月
        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        // 获取用户在日期范围内的打卡记录
        List<CheckinRecord> records = checkinRecordService.getCheckinRecords(userId, startDate, endDate);
        
        // 按运动类型分组统计
        Map<Long, Integer> sportTypeCount = new HashMap<>(); // 记录各运动类型的次数
        Map<Long, Integer> sportTypeScores = new HashMap<>(); // 记录各运动类型的积分
        
        for (CheckinRecord record : records) {
            Long sportTypeId = record.getSportTypeId();
            sportTypeCount.put(sportTypeId, sportTypeCount.getOrDefault(sportTypeId, 0) + 1);
            sportTypeScores.put(sportTypeId, sportTypeScores.getOrDefault(sportTypeId, 0) + record.getScore());
        }
        
        // 组装结果
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : sportTypeCount.entrySet()) {
            Long sportTypeId = entry.getKey();
            Integer count = entry.getValue();
            Integer score = sportTypeScores.get(sportTypeId);
            
            Map<String, Object> item = new HashMap<>();
            item.put("sportTypeId", sportTypeId);
            item.put("count", count);
            item.put("score", score);
            // TODO: 添加运动类型名称，需要从运动类型服务获取
            item.put("sportTypeName", getSportTypeName(sportTypeId));
            
            result.add(item);
        }
        
        // 按次数降序排序
        result.sort((a, b) -> ((Integer) b.get("count")).compareTo((Integer) a.get("count")));
        
        return result;
    }
    
    /**
     * 获取运动类型名称
     *
     * @param sportTypeId 运动类型ID
     * @return 运动类型名称
     */
    private String getSportTypeName(Long sportTypeId) {
        // TODO: 从运动类型服务获取名称
        // 这里简单模拟
        switch (sportTypeId.intValue()) {
            case 1:
                return "跑步";
            case 2:
                return "骑行";
            case 3:
                return "游泳";
            case 4:
                return "健身";
            default:
                return "其他运动";
        }
    }
}