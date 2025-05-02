package com.sportcheckin.service;

import com.sportcheckin.entity.CheckinRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 打卡记录服务接口
 */
public interface CheckinRecordService {

    /**
     * 添加打卡记录
     *
     * @param record 打卡记录
     * @return 添加后的打卡记录
     */
    CheckinRecord addCheckinRecord(CheckinRecord record);

    /**
     * 获取用户打卡记录列表
     *
     * @param userId    用户ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 打卡记录列表
     */
    List<CheckinRecord> getCheckinRecords(Long userId, LocalDate startDate, LocalDate endDate);

    /**
     * 获取用户本周打卡统计
     *
     * @param userId 用户ID
     * @return 本周打卡统计
     */
    Map<String, Object> getWeeklyStats(Long userId);

    /**
     * 计算积分
     *
     * @param sportTypeId 运动类型ID
     * @param amount      运动量
     * @return 积分
     */
    int calculateScore(Long sportTypeId, Double amount);

    /**
     * 检查用户当天是否已打卡
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 是否已打卡
     */
    boolean hasCheckedInToday(Long userId, LocalDate date);

    /**
     * 获取用户月度打卡统计
     *
     * @param userId 用户ID
     * @param year   年份
     * @param month  月份
     * @return 月度打卡统计
     */
    Map<String, Object> getMonthlyStats(Long userId, int year, int month);
}