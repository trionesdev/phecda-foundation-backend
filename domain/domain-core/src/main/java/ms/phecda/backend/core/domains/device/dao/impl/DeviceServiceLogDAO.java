package ms.phecda.backend.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.domains.device.dao.mapper.DeviceServiceLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class DeviceServiceLogDAO extends ServiceImpl<DeviceServiceLogMapper, DeviceServiceLog> {

    public Page<DeviceServiceLog> page(DeviceServiceLogCriteria criteria) {
        LambdaQueryWrapper<DeviceServiceLog> queryWrapper = buildQueryWrapper(criteria);
        Page<DeviceServiceLog> page = new Page<>(criteria.getPageNum(), criteria.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<DeviceServiceLog> buildQueryWrapper(DeviceServiceLogCriteria criteria) {
        LambdaQueryWrapper<DeviceServiceLog> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.isNull(criteria)) {
            return queryWrapper;
        }

        queryWrapper.eq(StringUtils.isNotBlank(criteria.getDeviceName()), DeviceServiceLog::getDeviceName, criteria.getDeviceName());
        queryWrapper.eq(StringUtils.isNotBlank(criteria.getServiceIdentifier()), DeviceServiceLog::getServiceIdentifier, criteria.getServiceIdentifier());
        queryWrapper.eq(StringUtils.isNotBlank(criteria.getServiceIdentifier()), DeviceServiceLog::getServiceIdentifier, criteria.getServiceIdentifier());
        queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), DeviceServiceLog::getTime, criteria.getStartTime());
        queryWrapper.le(Objects.nonNull(criteria.getEndTime()), DeviceServiceLog::getTime, criteria.getEndTime());

        return queryWrapper;
    }
}
