package com.trionesdev.phecda.backend.core.domains.alarm.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.backend.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import com.trionesdev.phecda.backend.core.domains.alarm.dao.entity.AlarmLevel;
import com.trionesdev.phecda.backend.core.domains.alarm.dao.mapper.AlarmLevelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AlarmLevelDAO extends ServiceImpl<AlarmLevelMapper, AlarmLevel> {
    private LambdaQueryWrapper<AlarmLevel> buildQueryWrapper(AlarmLevelCriteria criteria) {
        LambdaQueryWrapper<AlarmLevel> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            wrapper.eq(Objects.nonNull(criteria.getEnabled()), AlarmLevel::getEnabled, criteria.getEnabled());
        }
        return wrapper.orderByDesc(AlarmLevel::getCreatedAt);
    }

    public AlarmLevel selectByIdentifier(String identifier) {
        return lambdaQuery().eq(AlarmLevel::getIdentifier, identifier).last("limit 1").one();
    }

    public List<AlarmLevel> selectList(AlarmLevelCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

}
