package com.sportcheckin.controller;

import com.sportcheckin.dto.Result;
import com.sportcheckin.entity.SportType;
import com.sportcheckin.service.SportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运动类型控制器
 */
@RestController
@RequestMapping("/sport-type")
public class SportTypeController {

    @Autowired
    private SportTypeService sportTypeService;

    /**
     * 获取所有运动类型
     *
     * @return 运动类型列表
     */
    @GetMapping("/list")
    public Result<List<SportType>> getAllSportTypes() {
        List<SportType> sportTypes = sportTypeService.getAllSportTypes();
        return Result.success(sportTypes);
    }

    /**
     * 根据ID获取运动类型
     *
     * @param id 运动类型ID
     * @return 运动类型
     */
    @GetMapping("/{id}")
    public Result<SportType> getSportTypeById(@PathVariable("id") Long id) {
        SportType sportType = sportTypeService.getSportTypeById(id);
        return Result.success(sportType);
    }
    
    /**
     * 添加运动类型
     *
     * @param sportType 运动类型信息
     * @return 添加后的运动类型
     */
    @PostMapping("/add")
    public Result<SportType> addSportType(@RequestBody SportType sportType) {
        SportType added = sportTypeService.addSportType(sportType);
        return Result.success(added);
    }
    
    /**
     * 更新运动类型
     *
     * @param sportType 运动类型信息
     * @return 更新后的运动类型
     */
    @PutMapping("/update")
    public Result<SportType> updateSportType(@RequestBody SportType sportType) {
        SportType updated = sportTypeService.updateSportType(sportType);
        return Result.success(updated);
    }
    
    /**
     * 删除运动类型
     *
     * @param id 运动类型ID
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteSportType(@PathVariable("id") Long id) {
        boolean result = sportTypeService.deleteSportType(id);
        return Result.success(result);
    }
}