package ms.phecda.backend.core.domains.alarm.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmType;
import ms.phecda.backend.core.domains.alarm.dao.mapper.AlarmTypeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AlarmTypeDAO extends ServiceImpl<AlarmTypeMapper, AlarmType> {

    private LambdaQueryWrapper<AlarmType> builderQueryWrapper(AlarmTypeCriteria criteria) {
        LambdaQueryWrapper<AlarmType> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getEnabled()), AlarmType::getEnabled, criteria.getEnabled());
        }
        return queryWrapper.orderByDesc(AlarmType::getCreatedAt);
    }

    public AlarmType selectByIdentifier(String identifier) {
        return lambdaQuery().eq(AlarmType::getIdentifier, identifier).last("limit 1").one();
    }

    public List<AlarmType> selectList(AlarmTypeCriteria criteria) {
        return baseMapper.selectList(builderQueryWrapper(criteria));
    }

}
