package com.sportcheckin.service;

import com.sportcheckin.entity.SpecialRequest;

import java.util.List;

/**
 * 特殊情况申请服务接口
 */
public interface SpecialRequestService {

    /**
     * 提交特殊情况申请
     *
     * @param request 特殊情况申请
     * @return 提交后的申请
     */
    SpecialRequest submitSpecialRequest(SpecialRequest request);

    /**
     * 获取用户特殊情况申请列表
     *
     * @param userId 用户ID
     * @return 特殊情况申请列表
     */
    List<SpecialRequest> getSpecialRequests(Long userId);

    /**
     * 取消特殊情况申请
     *
     * @param id     申请ID
     * @param userId 用户ID（用于验证权限）
     * @return 是否取消成功
     */
    boolean cancelSpecialRequest(Long id, Long userId);

    /**
     * 审核特殊情况申请
     *
     * @param id           申请ID
     * @param status       审核状态 1-通过 2-拒绝
     * @param reviewRemark 审核备注
     * @return 审核后的申请
     */
    SpecialRequest reviewSpecialRequest(Long id, Integer status, String reviewRemark);

    /**
     * 根据ID获取特殊情况申请
     *
     * @param id 申请ID
     * @return 特殊情况申请
     */
    SpecialRequest getSpecialRequestById(Long id);

    /**
     * 检查用户在指定日期是否有有效的特殊情况申请
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 是否有有效申请
     */
    boolean hasValidSpecialRequest(Long userId, java.time.LocalDate date);
}