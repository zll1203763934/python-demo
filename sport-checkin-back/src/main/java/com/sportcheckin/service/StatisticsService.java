package com.sportcheckin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 统计服务接口
 */
public interface StatisticsService {

    /**
     * 获取用户周统计数据
     *
     * @param userId 用户ID
     * @param date   指定日期（获取该日期所在周的统计）
     * @return 周统计数据
     */
    Map<String, Object> getUserWeeklyStats(Long userId, LocalDate date);

    /**
     * 获取用户月统计数据
     *
     * @param userId 用户ID
     * @param year   年份
     * @param month  月份
     * @return 月统计数据
     */
    Map<String, Object> getUserMonthlyStats(Long userId, int year, int month);

    /**
     * 获取用户年统计数据
     *
     * @param userId 用户ID
     * @param year   年份
     * @return 年统计数据
     */
    Map<String, Object> getUserYearlyStats(Long userId, int year);

    /**
     * 获取周排行榜
     *
     * @param date 指定日期（获取该日期所在周的排行）
     * @param limit 限制数量
     * @return 排行榜数据
     */
    List<Map<String, Object>> getWeeklyRanking(LocalDate date, int limit);

    /**
     * 获取月排行榜
     *
     * @param year  年份
     * @param month 月份
     * @param limit 限制数量
     * @return 排行榜数据
     */
    List<Map<String, Object>> getMonthlyRanking(int year, int month, int limit);

    /**
     * 获取用户打卡日历数据
     *
     * @param userId 用户ID
     * @param year   年份
     * @param month  月份
     * @return 日历数据
     */
    Map<String, Object> getUserCheckinCalendar(Long userId, int year, int month);

    /**
     * 获取用户运动类型分布
     *
     * @param userId    用户ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 运动类型分布数据
     */
    List<Map<String, Object>> getUserSportTypeDistribution(Long userId, LocalDate startDate, LocalDate endDate);
}