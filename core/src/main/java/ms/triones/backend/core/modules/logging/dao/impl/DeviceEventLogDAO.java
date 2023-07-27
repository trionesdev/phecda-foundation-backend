package ms.triones.backend.core.modules.logging.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.triones.backend.core.modules.logging.dao.criteria.DeviceEventLogCriteria;
import ms.triones.backend.core.modules.logging.dao.entity.DeviceEventLog;
import ms.triones.backend.core.modules.logging.dao.mapper.DeviceEventLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class DeviceEventLogDAO extends ServiceImpl<DeviceEventLogMapper, DeviceEventLog> {

    public Page<DeviceEventLog> page(DeviceEventLogCriteria criteria) {
        LambdaQueryWrapper<DeviceEventLog> queryWrapper = buildQueryWrapper(criteria);
        Page<DeviceEventLog> page = new Page<>(criteria.getPageNum(), criteria.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<DeviceEventLog> buildQueryWrapper(DeviceEventLogCriteria criteria) {
        LambdaQueryWrapper<DeviceEventLog> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.isNull(criteria)) {
            return queryWrapper;
        }

        queryWrapper.eq(StringUtils.isNotBlank(criteria.getDeviceName()), DeviceEventLog::getDeviceName, criteria.getDeviceName());
        queryWrapper.eq(StringUtils.isNotBlank(criteria.getEventIdentifier()), DeviceEventLog::getEventIdentifier, criteria.getEventIdentifier());
        queryWrapper.eq(Objects.nonNull(criteria.getEventType()), DeviceEventLog::getEventType, criteria.getEventType());
        queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), DeviceEventLog::getTime, criteria.getStartTime());
        queryWrapper.le(Objects.nonNull(criteria.getEndTime()), DeviceEventLog::getTime, criteria.getEndTime());

        return queryWrapper;
    }
}
