package ms.phecda.backend.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.device.dao.po.DeviceEventLogPO;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.domains.device.dao.mapper.DeviceEventLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class DeviceEventLogDAO extends ServiceImpl<DeviceEventLogMapper, DeviceEventLogPO> {

    public Page<DeviceEventLogPO> page(DeviceEventLogCriteria criteria) {
        LambdaQueryWrapper<DeviceEventLogPO> queryWrapper = buildQueryWrapper(criteria);
        Page<DeviceEventLogPO> page = new Page<>(criteria.getPageNum(), criteria.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<DeviceEventLogPO> buildQueryWrapper(DeviceEventLogCriteria criteria) {
        LambdaQueryWrapper<DeviceEventLogPO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.isNull(criteria)) {
            return queryWrapper;
        }

        queryWrapper.eq(StringUtils.isNotBlank(criteria.getDeviceName()), DeviceEventLogPO::getDeviceName, criteria.getDeviceName());
        queryWrapper.eq(StringUtils.isNotBlank(criteria.getEventIdentifier()), DeviceEventLogPO::getEventIdentifier, criteria.getEventIdentifier());
        queryWrapper.eq(Objects.nonNull(criteria.getEventType()), DeviceEventLogPO::getEventType, criteria.getEventType());
        queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), DeviceEventLogPO::getTime, criteria.getStartTime());
        queryWrapper.le(Objects.nonNull(criteria.getEndTime()), DeviceEventLogPO::getTime, criteria.getEndTime());

        return queryWrapper;
    }
}
