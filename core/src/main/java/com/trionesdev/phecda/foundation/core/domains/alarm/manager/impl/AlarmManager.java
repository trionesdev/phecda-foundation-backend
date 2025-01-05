package com.trionesdev.phecda.foundation.core.domains.alarm.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.commons.exception.BusinessException;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmLevelPO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmPO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmTypePO;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.impl.AlarmDAO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.impl.AlarmLevelDAO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.impl.AlarmTypeDAO;
import com.trionesdev.phecda.foundation.core.domains.alarm.manager.dto.AlarmDTO;
import com.trionesdev.phecda.foundation.core.domains.alarm.internal.AlarmConvert;
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
    public void createType(AlarmTypePO alarmType) {
        AlarmTypePO alarmTypeSnap = alarmTypeDAO.selectByIdentifier(alarmType.getIdentifier());
        if (alarmTypeSnap != null) {
            throw new BusinessException("ALARM_TYPE_IDENTIFIER_DUPLICATED");
        }
        alarmTypeDAO.save(alarmType);
    }

    public void deleteTypeById(String id) {
        alarmTypeDAO.removeById(id);
    }

    public void updateTypeById(AlarmTypePO alarmType) {
        alarmTypeDAO.updateById(alarmType);
    }

    public Optional<AlarmTypePO> findTypeById(String id) {
        return Optional.ofNullable(alarmTypeDAO.getById(id));
    }

    public List<AlarmTypePO> findTypes(AlarmTypeCriteria criteria) {
        return alarmTypeDAO.selectList(criteria);
    }

    //endregion


    //region level
    public void createLevel(AlarmLevelPO alarmLevel) {
        AlarmLevelPO alarmLevelSnap = alarmLevelDAO.selectByIdentifier(alarmLevel.getIdentifier());
        if (alarmLevelSnap != null) {
            throw new BusinessException("ALARM_LEVEL_IDENTIFIER_DUPLICATED");
        }
        alarmLevelDAO.save(alarmLevel);
    }

    public void deleteLevelById(String id) {
        alarmLevelDAO.removeById(id);
    }

    public void updateLevelById(AlarmLevelPO alarmLevel) {
        alarmLevelDAO.updateById(alarmLevel);
    }

    public Optional<AlarmLevelPO> findLevelById(String id) {
        return Optional.ofNullable(alarmLevelDAO.getById(id));
    }

    public List<AlarmLevelPO> findLevels(AlarmLevelCriteria criteria) {
        return alarmLevelDAO.selectList(criteria);
    }

    //endregion


    //region alarm
    public void createAlarm(AlarmPO alarm) {
        alarmDAO.save(alarm);
    }

    public void deleteAlarmById(String id) {
        alarmDAO.removeById(id);
    }

    public void updateAlarmById(AlarmPO alarm) {
        alarmDAO.updateById(alarm);
    }

    public Optional<AlarmPO> findAlarmById(String id) {
        return Optional.ofNullable(alarmDAO.getById(id));
    }

    public List<AlarmDTO> findAlarmsExt(AlarmCriteria criteria) {
        List<AlarmPO> alarms = alarmDAO.selectList(criteria);
        return assembleAlarmExtBatch(alarms);
    }

    public PageInfo<AlarmPO> findAlarmsPage(AlarmCriteria criteria) {
        return alarmDAO.selectPage(criteria);
    }

    public PageInfo<AlarmDTO> findAlarmExtPage(AlarmCriteria criteria) {
        PageInfo<AlarmPO> pageInfo = alarmDAO.selectPage(criteria);
        return PageUtils.of(pageInfo, assembleAlarmExtBatch(pageInfo.getRows()));
    }

    private List<AlarmDTO> assembleAlarmExtBatch(List<AlarmPO> alarms) {
        if (CollectionUtil.isEmpty(alarms)) {
            return Collections.emptyList();
        }
        Map<String, AlarmTypePO> alarmTypeMap = alarmTypeDAO.list().stream().collect(Collectors.toMap(AlarmTypePO::getIdentifier, Function.identity(), (v1, v2) -> v1));
        Map<String, AlarmLevelPO> alarmLevelMap = alarmLevelDAO.list().stream().collect(Collectors.toMap(AlarmLevelPO::getIdentifier, Function.identity(), (v1, v2) -> v1));
        return alarms.stream().map(alarm -> {
            AlarmDTO alarmDTO = alarmConvert.from(alarm);
            alarmDTO.setTypeLabel(Optional.ofNullable(alarmTypeMap.get(alarm.getType())).map(AlarmTypePO::getName).orElse(null));
            alarmDTO.setLevelLabel(Optional.ofNullable(alarmLevelMap.get(alarm.getLevel())).map(AlarmLevelPO::getName).orElse(null));
            return alarmDTO;
        }).collect(Collectors.toList());
    }

    public Long queryCount(AlarmCriteria criteria){
        return alarmDAO.selectCount(criteria);
    }

    //endregion

}
