package com.sportcheckin.service;

import com.sportcheckin.entity.SportType;

import java.util.List;

/**
 * 运动类型服务接口
 */
public interface SportTypeService {

    /**
     * 获取所有运动类型
     *
     * @return 运动类型列表
     */
    List<SportType> getAllSportTypes();

    /**
     * 根据ID获取运动类型
     *
     * @param id 运动类型ID
     * @return 运动类型
     */
    SportType getSportTypeById(Long id);
    
    /**
     * 添加运动类型
     *
     * @param sportType 运动类型信息
     * @return 添加后的运动类型
     */
    SportType addSportType(SportType sportType);
    
    /**
     * 更新运动类型
     *
     * @param sportType 运动类型信息
     * @return 更新后的运动类型
     */
    SportType updateSportType(SportType sportType);
    
    /**
     * 删除运动类型
     *
     * @param id 运动类型ID
     * @return 是否删除成功
     */
    boolean deleteSportType(Long id);
}