package com.trionesdev.phecda.foundation.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceCommandLogPO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.mapper.DeviceServiceLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class DeviceServiceLogDAO extends ServiceImpl<DeviceServiceLogMapper, DeviceCommandLogPO> {

    public Page<DeviceCommandLogPO> page(DeviceServiceLogCriteria criteria) {
        LambdaQueryWrapper<DeviceCommandLogPO> queryWrapper = buildQueryWrapper(criteria);
        Page<DeviceCommandLogPO> page = new Page<>(criteria.getPageNum(), criteria.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<DeviceCommandLogPO> buildQueryWrapper(DeviceServiceLogCriteria criteria) {
        LambdaQueryWrapper<DeviceCommandLogPO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.isNull(criteria)) {
            return queryWrapper;
        }

        queryWrapper.eq(StringUtils.isNotBlank(criteria.getDeviceName()), DeviceCommandLogPO::getDeviceName, criteria.getDeviceName());
        queryWrapper.eq(StringUtils.isNotBlank(criteria.getServiceIdentifier()), DeviceCommandLogPO::getServiceIdentifier, criteria.getServiceIdentifier());
        queryWrapper.eq(StringUtils.isNotBlank(criteria.getServiceIdentifier()), DeviceCommandLogPO::getServiceIdentifier, criteria.getServiceIdentifier());
        queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), DeviceCommandLogPO::getTime, criteria.getStartTime());
        queryWrapper.le(Objects.nonNull(criteria.getEndTime()), DeviceCommandLogPO::getTime, criteria.getEndTime());

        return queryWrapper;
    }
}
