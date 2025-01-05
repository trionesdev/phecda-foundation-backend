package com.trionesdev.phecda.foundation.core.domains.alarm.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmLevelPO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.mapper.AlarmLevelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AlarmLevelDAO extends ServiceImpl<AlarmLevelMapper, AlarmLevelPO> {
    private LambdaQueryWrapper<AlarmLevelPO> buildQueryWrapper(AlarmLevelCriteria criteria) {
        LambdaQueryWrapper<AlarmLevelPO> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            wrapper.eq(Objects.nonNull(criteria.getEnabled()), AlarmLevelPO::getEnabled, criteria.getEnabled());
        }
        return wrapper.orderByDesc(AlarmLevelPO::getCreatedAt);
    }

    public AlarmLevelPO selectByIdentifier(String identifier) {
        return lambdaQuery().eq(AlarmLevelPO::getIdentifier, identifier).last("limit 1").one();
    }

    public List<AlarmLevelPO> selectList(AlarmLevelCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

}
