package ms.triones.backend.core.modules.alarm.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import ms.triones.backend.core.modules.alarm.dao.criteria.AlarmLogCriteria;
import ms.triones.backend.core.modules.alarm.dao.entity.AlarmLog;
import ms.triones.backend.core.modules.alarm.dao.mapper.AlarmLogMapper;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 告警记录 服务实现类
 * </p>
 *
 * @author jscoe
 * @since 2023-07-11
 */

@Repository
public class AlarmLogDAO extends ServiceImpl<AlarmLogMapper, AlarmLog> {

    private LambdaQueryWrapper<AlarmLog> buildQueryWrapper(AlarmLogCriteria criteria) {
        LambdaQueryWrapper<AlarmLog> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(StringUtils.isNotBlank(criteria.getTitle()), AlarmLog::getTitle, criteria.getTitle());

        }
        return queryWrapper;
    }

    public PageInfo<AlarmLog> selectPage(Integer pageNum, Integer pageSize, AlarmLogCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public List<AlarmLog> selectList(AlarmLogCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }
}
