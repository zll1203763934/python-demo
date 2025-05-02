package com.sportcheckin.mapper;

import com.sportcheckin.entity.SpecialRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 特殊情况申请Mapper接口
 */
@Mapper
public interface SpecialRequestMapper {

    /**
     * 添加特殊情况申请
     *
     * @param request 特殊情况申请
     * @return 影响行数
     */
    int insert(SpecialRequest request);

    /**
     * 根据ID查询特殊情况申请
     *
     * @param id 申请ID
     * @return 特殊情况申请
     */
    SpecialRequest selectById(Long id);

    /**
     * 查询用户特殊情况申请列表
     *
     * @param userId 用户ID
     * @return 特殊情况申请列表
     */
    List<SpecialRequest> selectByUserId(Long userId);

    /**
     * 更新特殊情况申请
     *
     * @param request 特殊情况申请
     * @return 影响行数
     */
    int update(SpecialRequest request);

    /**
     * 删除特殊情况申请
     *
     * @param id 申请ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 查询用户在指定日期范围内的有效特殊情况申请
     *
     * @param userId    用户ID
     * @param date      日期
     * @param status    状态（可选）
     * @return 特殊情况申请列表
     */
    List<SpecialRequest> selectValidRequestsByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date,
            @Param("status") Integer status);

    /**
     * 查询待审核的特殊情况申请列表
     *
     * @return 待审核的特殊情况申请列表
     */
    List<SpecialRequest> selectPendingRequests();

    /**
     * 批量更新特殊情况申请状态
     *
     * @param ids    申请ID列表
     * @param status 状态
     * @return 影响行数
     */
    int updateStatusBatch(@Param("ids") List<Long> ids, @Param("status") Integer status);
}