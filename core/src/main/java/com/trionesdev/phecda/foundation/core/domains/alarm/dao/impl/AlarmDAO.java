package com.trionesdev.phecda.foundation.core.domains.alarm.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmPO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.mapper.AlarmMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AlarmDAO extends ServiceImpl<AlarmMapper, AlarmPO> {

    private LambdaQueryWrapper<AlarmPO> buildQueryWrapper(AlarmCriteria criteria) {
        LambdaQueryWrapper<AlarmPO> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(StrUtil.isNotBlank(criteria.getType()), AlarmPO::getType, criteria.getType())
                    .eq(StrUtil.isNotBlank(criteria.getLevel()), AlarmPO::getLevel, criteria.getLevel())
                    .eq(StrUtil.isNotBlank(criteria.getProductKey()), AlarmPO::getProductKey, criteria.getProductKey())
                    .eq(StrUtil.isNotBlank(criteria.getDeviceName()), AlarmPO::getDeviceName, criteria.getDeviceName())
                    .gt(Objects.nonNull(criteria.getStartTime()), AlarmPO::getCreatedAt, criteria.getStartTime())
                    .lt(Objects.nonNull(criteria.getEndTime()), AlarmPO::getCreatedAt, criteria.getEndTime())
                    .eq(Objects.nonNull(criteria.getStatus()), AlarmPO::getStatus, criteria.getStatus())
            ;
        }
        return queryWrapper;
    }

    public List<AlarmPO> selectList(AlarmCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria).func(wrapper -> {
            if (Objects.nonNull(criteria.getLimit())) {
                wrapper.last("limit " + criteria.getLimit());
            } else {
                wrapper.last("limit 1000");
            }
        }).orderByDesc(AlarmPO::getCreatedAt));
    }

    public PageInfo<AlarmPO> selectPage(AlarmCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), buildQueryWrapper(criteria).orderByDesc(AlarmPO::getCreatedAt)));
    }

    public Long selectCount(AlarmCriteria criteria) {
        return baseMapper.selectCount(buildQueryWrapper(criteria));
    }

}
