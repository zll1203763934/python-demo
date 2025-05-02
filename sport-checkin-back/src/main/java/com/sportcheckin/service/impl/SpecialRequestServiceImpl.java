package com.sportcheckin.service.impl;

import com.sportcheckin.entity.SpecialRequest;
import com.sportcheckin.service.SpecialRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 特殊情况申请服务实现类
 */
@Service
public class SpecialRequestServiceImpl implements SpecialRequestService {

    @Override
    public SpecialRequest submitSpecialRequest(SpecialRequest request) {
        // TODO: 实现提交特殊情况申请逻辑
        // 1. 验证用户身份
        // 2. 验证申请日期
        // 3. 保存申请
        
        // 设置初始状态和时间
        request.setStatus(0); // 待审核
        LocalDateTime now = LocalDateTime.now();
        request.setCreateTime(now);
        request.setUpdateTime(now);
        
        // 模拟保存并返回
        request.setId(1L); // 模拟ID生成
        return request;
    }

    @Override
    public List<SpecialRequest> getSpecialRequests(Long userId) {
        // TODO: 实现查询特殊情况申请逻辑
        // 根据用户ID查询特殊情况申请
        
        // 模拟返回数据
        List<SpecialRequest> requests = new ArrayList<>();
        
        // 模拟生成一些申请记录
        for (int i = 1; i <= 3; i++) {
            SpecialRequest request = new SpecialRequest();
            request.setId((long) i);
            request.setUserId(userId);
            request.setType(i % 3 + 1); // 1-生理期 2-生病 3-其他
            
            switch (request.getType()) {
                case 1:
                    request.setReason("生理期不适");
                    break;
                case 2:
                    request.setReason("感冒发烧");
                    break;
                case 3:
                    request.setReason("家庭紧急事务");
                    break;
            }
            
            LocalDate now = LocalDate.now();
            request.setStartDate(now.minusDays(i));
            request.setEndDate(now.plusDays(i));
            request.setStatus(i % 3); // 0-待审核 1-已通过 2-已拒绝
            
            if (request.getStatus() > 0) {
                request.setReviewRemark(request.getStatus() == 1 ? "申请通过" : "申请不符合要求");
            }
            
            requests.add(request);
        }
        
        return requests;
    }

    @Override
    public boolean cancelSpecialRequest(Long id, Long userId) {
        // TODO: 实现取消特殊情况申请逻辑
        // 1. 验证用户身份
        // 2. 验证申请状态
        // 3. 删除或标记取消
        
        // 模拟取消成功
        return true;
    }

    @Override
    public SpecialRequest reviewSpecialRequest(Long id, Integer status, String reviewRemark) {
        // TODO: 实现审核特殊情况申请逻辑
        // 1. 获取申请
        // 2. 更新状态和审核备注
        // 3. 保存更新
        
        SpecialRequest request = getSpecialRequestById(id);
        if (request != null) {
            request.setStatus(status);
            request.setReviewRemark(reviewRemark);
            request.setUpdateTime(LocalDateTime.now());
            // 模拟保存更新
        }
        
        return request;
    }

    @Override
    public SpecialRequest getSpecialRequestById(Long id) {
        // TODO: 实现根据ID查询特殊情况申请逻辑
        
        // 模拟返回数据
        SpecialRequest request = new SpecialRequest();
        request.setId(id);
        request.setUserId(1L);
        request.setType(1); // 生理期
        request.setReason("生理期不适");
        request.setStartDate(LocalDate.now().minusDays(1));
        request.setEndDate(LocalDate.now().plusDays(3));
        request.setStatus(0); // 待审核
        
        return request;
    }

    @Override
    public boolean hasValidSpecialRequest(Long userId, LocalDate date) {
        // TODO: 实现检查用户在指定日期是否有有效的特殊情况申请逻辑
        // 1. 查询用户所有已通过的特殊情况申请
        // 2. 检查日期是否在申请范围内
        
        // 模拟检查结果
        List<SpecialRequest> requests = getSpecialRequests(userId);
        for (SpecialRequest request : requests) {
            // 只检查已通过的申请
            if (request.getStatus() == 1) {
                // 检查日期是否在申请范围内
                if (!date.isBefore(request.getStartDate()) && !date.isAfter(request.getEndDate())) {
                    return true;
                }
            }
        }
        
        return false;
    }
}