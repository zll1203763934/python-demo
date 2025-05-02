package com.sportcheckin.controller;

import com.sportcheckin.dto.Result;
import com.sportcheckin.entity.SpecialRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 特殊情况申请控制器
 */
@RestController
@RequestMapping("/special-request")
public class SpecialRequestController {

    /**
     * 提交特殊情况申请
     *
     * @param request 特殊情况申请
     * @return 申请结果
     */
    @PostMapping
    public Result<SpecialRequest> submitSpecialRequest(@RequestBody SpecialRequest request) {
        // TODO: 实现提交特殊情况申请逻辑
        // 1. 验证用户身份
        // 2. 验证申请日期
        // 3. 保存申请
        
        // 模拟添加成功
        request.setId(1L);
        request.setStatus(0); // 待审核
        
        return Result.success(request);
    }

    /**
     * 获取用户特殊情况申请列表
     *
     * @param userId 用户ID
     * @return 特殊情况申请列表
     */
    @GetMapping("/list")
    public Result<List<SpecialRequest>> getSpecialRequests(@RequestParam("userId") Long userId) {
        // TODO: 实现查询特殊情况申请逻辑
        // 模拟返回数据
        List<SpecialRequest> requests = new ArrayList<>();
        
        SpecialRequest request = new SpecialRequest();
        request.setId(1L);
        request.setUserId(userId);
        request.setType(1); // 生理期
        request.setReason("生理期不适");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(5));
        request.setStatus(1); // 已通过
        requests.add(request);
        
        return Result.success(requests);
    }

    /**
     * 取消特殊情况申请
     *
     * @param id 申请ID
     * @return 取消结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> cancelSpecialRequest(@PathVariable("id") Long id) {
        // TODO: 实现取消特殊情况申请逻辑
        // 1. 验证用户身份
        // 2. 验证申请状态
        // 3. 删除或标记取消
        
        return Result.success();
    }
}