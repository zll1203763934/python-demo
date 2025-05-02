package com.sportcheckin.service.impl;

import com.sportcheckin.entity.SportType;
import com.sportcheckin.mapper.SportTypeMapper;
import com.sportcheckin.service.SportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运动类型服务实现类
 */
@Service
public class SportTypeServiceImpl implements SportTypeService {

    @Autowired
    private SportTypeMapper sportTypeMapper;

    @Override
    public List<SportType> getAllSportTypes() {
        return sportTypeMapper.selectAll();
    }

    @Override
    public SportType getSportTypeById(Long id) {
        return sportTypeMapper.selectById(id);
    }

    @Override
    public SportType addSportType(SportType sportType) {
        sportTypeMapper.insert(sportType);
        return sportType;
    }

    @Override
    public SportType updateSportType(SportType sportType) {
        sportTypeMapper.update(sportType);
        return sportType;
    }

    @Override
    public boolean deleteSportType(Long id) {
        return sportTypeMapper.deleteById(id) > 0;
    }
}