package ms.phecda.backend.core.domains.alarm.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.commons.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmCriteria;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import ms.phecda.backend.core.domains.alarm.dao.entity.Alarm;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmLevel;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmType;
import ms.phecda.backend.core.domains.alarm.dao.impl.AlarmDAO;
import ms.phecda.backend.core.domains.alarm.dao.impl.AlarmLevelDAO;
import ms.phecda.backend.core.domains.alarm.dao.impl.AlarmTypeDAO;
import ms.phecda.backend.core.domains.alarm.manager.dto.AlarmDTO;
import ms.phecda.backend.core.domains.alarm.internal.AlarmConvert;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmManager {
    private final AlarmConvert alarmConvert;
    private final AlarmTypeDAO alarmTypeDAO;
    private final AlarmLevelDAO alarmLevelDAO;
    private final AlarmDAO alarmDAO;

    //region type
    public void createType(AlarmType alarmType) {
        AlarmType alarmTypeSnap = alarmTypeDAO.selectByIdentifier(alarmType.getIdentifier());
        if (alarmTypeSnap != null) {
            throw new BusinessException("ALARM_TYPE_IDENTIFIER_DUPLICATED");
        }
        alarmTypeDAO.save(alarmType);
    }

    public void deleteTypeById(String id) {
        alarmTypeDAO.removeById(id);
    }

    public void updateTypeById(AlarmType alarmType) {
        alarmTypeDAO.updateById(alarmType);
    }

    public Optional<AlarmType> findTypeById(String id) {
        return Optional.ofNullable(alarmTypeDAO.getById(id));
    }

    public List<AlarmType> findTypes(AlarmTypeCriteria criteria) {
        return alarmTypeDAO.selectList(criteria);
    }

    //endregion


    //region level
    public void createLevel(AlarmLevel alarmLevel) {
        AlarmLevel alarmLevelSnap = alarmLevelDAO.selectByIdentifier(alarmLevel.getIdentifier());
        if (alarmLevelSnap != null) {
            throw new BusinessException("ALARM_LEVEL_IDENTIFIER_DUPLICATED");
        }
        alarmLevelDAO.save(alarmLevel);
    }

    public void deleteLevelById(String id) {
        alarmLevelDAO.removeById(id);
    }

    public void updateLevelById(AlarmLevel alarmLevel) {
        alarmLevelDAO.updateById(alarmLevel);
    }

    public Optional<AlarmLevel> findLevelById(String id) {
        return Optional.ofNullable(alarmLevelDAO.getById(id));
    }

    public List<AlarmLevel> findLevels(AlarmLevelCriteria criteria) {
        return alarmLevelDAO.selectList(criteria);
    }

    //endregion


    //region alarm
    public void createAlarm(Alarm alarm) {
        alarmDAO.save(alarm);
    }

    public void deleteAlarmById(String id) {
        alarmDAO.removeById(id);
    }

    public void updateAlarmById(Alarm alarm) {
        alarmDAO.updateById(alarm);
    }

    public Optional<Alarm> findAlarmById(String id) {
        return Optional.ofNullable(alarmDAO.getById(id));
    }

    public List<AlarmDTO> findAlarmsExt(AlarmCriteria criteria) {
        List<Alarm> alarms = alarmDAO.selectList(criteria);
        return assembleAlarmExtBatch(alarms);
    }

    public PageInfo<Alarm> findAlarmsPage(AlarmCriteria criteria) {
        return alarmDAO.selectPage(criteria);
    }

    public PageInfo<AlarmDTO> findAlarmExtPage(AlarmCriteria criteria) {
        PageInfo<Alarm> pageInfo = alarmDAO.selectPage(criteria);
        return PageUtils.of(pageInfo, assembleAlarmExtBatch(pageInfo.getRows()));
    }

    private List<AlarmDTO> assembleAlarmExtBatch(List<Alarm> alarms) {
        if (CollectionUtil.isEmpty(alarms)) {
            return Collections.emptyList();
        }
        Map<String, AlarmType> alarmTypeMap = alarmTypeDAO.list().stream().collect(Collectors.toMap(AlarmType::getIdentifier, Function.identity(), (v1, v2) -> v1));
        Map<String, AlarmLevel> alarmLevelMap = alarmLevelDAO.list().stream().collect(Collectors.toMap(AlarmLevel::getIdentifier, Function.identity(), (v1, v2) -> v1));
        return alarms.stream().map(alarm -> {
            AlarmDTO alarmDTO = alarmConvert.from(alarm);
            alarmDTO.setTypeLabel(Optional.ofNullable(alarmTypeMap.get(alarm.getType())).map(AlarmType::getName).orElse(null));
            alarmDTO.setLevelLabel(Optional.ofNullable(alarmLevelMap.get(alarm.getLevel())).map(AlarmLevel::getName).orElse(null));
            return alarmDTO;
        }).collect(Collectors.toList());
    }

    public Long queryCount(AlarmCriteria criteria){
        return alarmDAO.selectCount(criteria);
    }

    //endregion

}
