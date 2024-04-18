package ms.phecda.backend.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceStatisticsMessageDailyCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceStatisticsMessageDaily;
import ms.phecda.backend.core.domains.device.dao.mapper.DeviceStatisticsMessageDailyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DeviceStatisticsMessageDailyDAO extends ServiceImpl<DeviceStatisticsMessageDailyMapper, DeviceStatisticsMessageDaily> {

    private LambdaQueryWrapper<DeviceStatisticsMessageDaily> buildLambdaQueryWrapper(DeviceStatisticsMessageDailyCriteria criteria) {
        LambdaQueryWrapper<DeviceStatisticsMessageDaily> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), DeviceStatisticsMessageDaily::getDate, criteria.getStartTime())
                    .le(Objects.nonNull(criteria.getEndTime()), DeviceStatisticsMessageDaily::getDate, criteria.getEndTime())
                    .eq(Objects.nonNull(criteria.getType()), DeviceStatisticsMessageDaily::getType, criteria.getType())
                    .eq(Objects.nonNull(criteria.getDate()), DeviceStatisticsMessageDaily::getDate, criteria.getDate())
            ;
        }
        return queryWrapper;
    }

    private QueryWrapper<DeviceStatisticsMessageDaily> buildQueryWrapper(DeviceStatisticsMessageDailyCriteria criteria) {
        QueryWrapper<DeviceStatisticsMessageDaily> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.ge(Objects.nonNull(criteria.getStartTime()), "date", criteria.getStartTime())
                    .le(Objects.nonNull(criteria.getEndTime()), "date", criteria.getEndTime());
        }
        return queryWrapper;
    }

    public Long selectQuantitySum(DeviceStatisticsMessageDailyCriteria criteria) {
        Map<String, Object> map = this.getMap(buildQueryWrapper(criteria).select(" sum(quantity) as totalQuantity "));
        return Optional.ofNullable(map.get("totalQuantity")).map(t -> Long.parseLong(t.toString())).orElse(0L);
    }

    public List<DeviceStatisticsMessageDaily> selectList(DeviceStatisticsMessageDailyCriteria criteria) {
        return this.baseMapper.selectList(buildLambdaQueryWrapper(criteria));
    }

}
