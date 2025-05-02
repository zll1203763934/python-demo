package com.sportcheckin.controller;

import com.sportcheckin.dto.Result;
import com.sportcheckin.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 统计控制器
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获取用户周统计数据
     *
     * @param userId 用户ID
     * @param date   指定日期（获取该日期所在周的统计）
     * @return 周统计数据
     */
    @GetMapping("/weekly")
    public Result<Map<String, Object>> getUserWeeklyStats(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Map<String, Object> stats = statisticsService.getUserWeeklyStats(userId, date);
        return Result.success(stats);
    }

    /**
     * 获取用户月统计数据
     *
     * @param userId 用户ID
     * @param year   年份
     * @param month  月份
     * @return 月统计数据
     */
    @GetMapping("/monthly")
    public Result<Map<String, Object>> getUserMonthlyStats(
            @RequestParam("userId") Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        Map<String, Object> stats = statisticsService.getUserMonthlyStats(userId, year, month);
        return Result.success(stats);
    }

    /**
     * 获取用户年统计数据
     *
     * @param userId 用户ID
     * @param year   年份
     * @return 年统计数据
     */
    @GetMapping("/yearly")
    public Result<Map<String, Object>> getUserYearlyStats(
            @RequestParam("userId") Long userId,
            @RequestParam("year") int year) {
        Map<String, Object> stats = statisticsService.getUserYearlyStats(userId, year);
        return Result.success(stats);
    }

    /**
     * 获取周排行榜
     *
     * @param date  指定日期（获取该日期所在周的排行）
     * @param limit 限制数量
     * @return 排行榜数据
     */
    @GetMapping("/ranking/weekly")
    public Result<List<Map<String, Object>>> getWeeklyRanking(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Map<String, Object>> ranking = statisticsService.getWeeklyRanking(date, limit);
        return Result.success(ranking);
    }

    /**
     * 获取月排行榜
     *
     * @param year  年份
     * @param month 月份
     * @param limit 限制数量
     * @return 排行榜数据
     */
    @GetMapping("/ranking/monthly")
    public Result<List<Map<String, Object>>> getMonthlyRanking(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Map<String, Object>> ranking = statisticsService.getMonthlyRanking(year, month, limit);
        return Result.success(ranking);
    }

    /**
     * 获取用户打卡日历数据
     *
     * @param userId 用户ID
     * @param year   年份
     * @param month  月份
     * @return 日历数据
     */
    @GetMapping("/calendar")
    public Result<Map<String, Object>> getUserCheckinCalendar(
            @RequestParam("userId") Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        Map<String, Object> calendar = statisticsService.getUserCheckinCalendar(userId, year, month);
        return Result.success(calendar);
    }

    /**
     * 获取用户运动类型分布
     *
     * @param userId    用户ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 运动类型分布数据
     */
    @GetMapping("/sport-type-distribution")
    public Result<List<Map<String, Object>>> getUserSportTypeDistribution(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Map<String, Object>> distribution = statisticsService.getUserSportTypeDistribution(userId, startDate, endDate);
        return Result.success(distribution);
    }
}