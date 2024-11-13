package com.trionesdev.phecda.backend.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DeviceStatisticsMessageDailyCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DeviceStatisticsMessageDailyPO;
import com.trionesdev.phecda.backend.core.domains.device.dao.mapper.DeviceStatisticsMessageDailyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DeviceStatisticsMessageDailyDAO extends ServiceImpl<DeviceStatisticsMessageDailyMapper, DeviceStatisticsMessageDailyPO> {

    private LambdaQueryWrapper<DeviceStatisticsMessageDailyPO> buildLambdaQueryWrapper(DeviceStatisticsMessageDailyCriteria criteria) {
        LambdaQueryWrapper<DeviceStatisticsMessageDailyPO> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), DeviceStatisticsMessageDailyPO::getDate, criteria.getStartTime())
                    .le(Objects.nonNull(criteria.getEndTime()), DeviceStatisticsMessageDailyPO::getDate, criteria.getEndTime())
                    .eq(Objects.nonNull(criteria.getType()), DeviceStatisticsMessageDailyPO::getType, criteria.getType())
                    .eq(Objects.nonNull(criteria.getDate()), DeviceStatisticsMessageDailyPO::getDate, criteria.getDate())
            ;
        }
        return queryWrapper;
    }

    private QueryWrapper<DeviceStatisticsMessageDailyPO> buildQueryWrapper(DeviceStatisticsMessageDailyCriteria criteria) {
        QueryWrapper<DeviceStatisticsMessageDailyPO> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), "date", criteria.getStartTime())
                    .le(Objects.nonNull(criteria.getEndTime()), "date", criteria.getEndTime());
        }
        return queryWrapper;
    }

    public Long selectQuantitySum(DeviceStatisticsMessageDailyCriteria criteria) {
        Map<String, Object> map = this.getMap(buildQueryWrapper(criteria).select(" sum(quantity) as totalQuantity "));
        return Optional.ofNullable(map).map(t -> t.get("totalQuantity")).map(t -> Long.parseLong(t.toString())).orElse(0L);
    }

    public List<DeviceStatisticsMessageDailyPO> selectList(DeviceStatisticsMessageDailyCriteria criteria) {
        return this.baseMapper.selectList(buildLambdaQueryWrapper(criteria));
    }

}
