package ms.triones.backend.core.modules.alarm.manager;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.alarm.dao.criteria.AlarmLogCriteria;
import ms.triones.backend.core.modules.alarm.dao.entity.AlarmLog;
import ms.triones.backend.core.modules.alarm.dao.impl.AlarmLogDAO;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import ms.triones.backend.core.modules.dict.dao.impl.DictionaryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class AlarmLogManager {
    private final AlarmLogDAO alarmLogDAO;

    public void create(AlarmLog entity) {
        alarmLogDAO.save(entity);
    }

    public void deleteById(String id) {
        alarmLogDAO.removeById(id);
    }

    public void updateById(AlarmLog entity) {
        alarmLogDAO.updateById(entity);
    }

    public Optional<AlarmLog> queryById(String id) {
        return Optional.ofNullable(alarmLogDAO.getById(id));
    }

    public PageInfo<AlarmLog> queryPage(Integer pageNum, Integer pageSize, AlarmLogCriteria criteria) {
        return alarmLogDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<AlarmLog> queryList(AlarmLogCriteria criteria) {
        return alarmLogDAO.selectList(criteria);
    }
    
}
