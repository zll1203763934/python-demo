package com.sportcheckin.mapper;

import com.sportcheckin.entity.CheckinRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 打卡记录Mapper接口
 */
@Mapper
public interface CheckinRecordMapper {

    /**
     * 添加打卡记录
     *
     * @param record 打卡记录
     * @return 影响行数
     */
    int insert(CheckinRecord record);

    /**
     * 根据ID查询打卡记录
     *
     * @param id 记录ID
     * @return 打卡记录
     */
    CheckinRecord selectById(Long id);

    /**
     * 查询用户打卡记录列表
     *
     * @param userId    用户ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 打卡记录列表
     */
    List<CheckinRecord> selectByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * 查询用户当天是否已打卡
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 打卡记录数
     */
    int countByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    /**
     * 更新打卡记录
     *
     * @param record 打卡记录
     * @return 影响行数
     */
    int update(CheckinRecord record);

    /**
     * 删除打卡记录
     *
     * @param id 记录ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 统计用户在日期范围内的打卡总积分
     *
     * @param userId    用户ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 总积分
     */
    int sumScoreByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * 统计用户在日期范围内的打卡天数
     *
     * @param userId    用户ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 打卡天数
     */
    int countDistinctDateByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}