package ms.triones.backend.core.modules.alarm.service.impl;

import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.exception.MSException;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.alarm.dao.criteria.AlarmLogCriteria;
import ms.triones.backend.core.modules.alarm.dao.entity.AlarmLog;
import ms.triones.backend.core.modules.alarm.manager.AlarmLogManager;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import ms.triones.backend.core.modules.dict.manager.DictionaryManager;
import ms.triones.backend.core.modules.dict.service.bo.DictionaryBO;
import ms.triones.backend.core.modules.dict.support.DictionaryConvertMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 告警记录 服务类
 * </p>
 *
 * @author jscoe
 * @since 2023-07-11
 */

@RequiredArgsConstructor
@Service
public class AlarmLogService {

    private final AlarmLogManager alarmLogManager;

    public void create(AlarmLog entity) {
        alarmLogManager.create(entity);
    }

    public void deleteById(String id) {
        alarmLogManager.deleteById(id);
    }

    public void update(AlarmLog entity) {
        alarmLogManager.updateById(entity);
    }

    public Optional<AlarmLog> queryById(String id) {
        return alarmLogManager.queryById(id);
    }

    public PageInfo<AlarmLog> queryPage(Integer pageNum, Integer pageSize, AlarmLogCriteria criteria) {
        return alarmLogManager.queryPage(pageNum, pageSize, criteria);
    }

    public List<AlarmLog> queryList(AlarmLogCriteria criteria) {
        return alarmLogManager.queryList(criteria);
    }
}
