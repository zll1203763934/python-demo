package com.sportcheckin.mapper;

import com.sportcheckin.entity.SportType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 运动类型Mapper接口
 */
@Mapper
public interface SportTypeMapper {

    /**
     * 查询所有运动类型
     *
     * @return 运动类型列表
     */
    List<SportType> selectAll();

    /**
     * 根据ID查询运动类型
     *
     * @param id 运动类型ID
     * @return 运动类型
     */
    SportType selectById(Long id);

    /**
     * 添加运动类型
     *
     * @param sportType 运动类型信息
     * @return 影响行数
     */
    int insert(SportType sportType);

    /**
     * 更新运动类型
     *
     * @param sportType 运动类型信息
     * @return 影响行数
     */
    int update(SportType sportType);

    /**
     * 删除运动类型
     *
     * @param id 运动类型ID
     * @return 影响行数
     */
    int deleteById(Long id);
}