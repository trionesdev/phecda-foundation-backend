package com.trionesdev.phecda.foundation.core.domains.alarm.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmTypePO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.mapper.AlarmTypeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AlarmTypeDAO extends ServiceImpl<AlarmTypeMapper, AlarmTypePO> {

    private LambdaQueryWrapper<AlarmTypePO> builderQueryWrapper(AlarmTypeCriteria criteria) {
        LambdaQueryWrapper<AlarmTypePO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getEnabled()), AlarmTypePO::getEnabled, criteria.getEnabled());
        }
        return queryWrapper.orderByDesc(AlarmTypePO::getCreatedAt);
    }

    public AlarmTypePO selectByIdentifier(String identifier) {
        return lambdaQuery().eq(AlarmTypePO::getIdentifier, identifier).last("limit 1").one();
    }

    public List<AlarmTypePO> selectList(AlarmTypeCriteria criteria) {
        return baseMapper.selectList(builderQueryWrapper(criteria));
    }

}
