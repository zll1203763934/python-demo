package com.sportcheckin.dto;

import lombok.Data;

/**
 * 通用响应结果类
 */
@Data
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 数据
     */
    private T data;
    
    /**
     * 成功结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }
    
    /**
     * 成功结果（带数据）
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
    
    /**
     * 失败结果
     */
    public static <T> Result<T> fail(String message) {
        return fail(500, message);
    }
    
    /**
     * 失败结果（带状态码）
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    /**
     * 失败结果（带状态码和数据）
     */
    public static <T> Result<T> fail(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
        
    }
}