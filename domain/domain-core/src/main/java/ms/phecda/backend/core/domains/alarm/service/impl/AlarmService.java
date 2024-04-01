package ms.phecda.backend.core.domains.alarm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmCriteria;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import ms.phecda.backend.core.domains.alarm.dao.entity.Alarm;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmLevel;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmType;
import ms.phecda.backend.core.domains.alarm.manager.dto.AlarmDTO;
import ms.phecda.backend.core.domains.alarm.manager.impl.AlarmManager;
import ms.phecda.backend.core.domains.alarm.service.bo.AlarmCreateArgBO;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.provider.ssp.device.impl.DeviceProvider;
import ms.phecda.backend.core.provider.ssp.device.pdo.ProductPDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.thingmodel.ThingModelPropertyPDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmManager alarmManager;
    private final DeviceProvider deviceProvider;

    //region type
    public void createType(AlarmType alarmType) {
        alarmManager.createType(alarmType);
    }

    public void deleteTypeById(String typeId) {
        alarmManager.deleteTypeById(typeId);
    }

    public void updateTypeById(AlarmType alarmType) {
        alarmManager.updateTypeById(alarmType);
    }

    public void changeTypeEnabledById(AlarmType alarmType) {
        alarmManager.updateTypeById(alarmType);
    }


    public Optional<AlarmType> findTypeById(String typeId) {
        return alarmManager.findTypeById(typeId);
    }

    public List<AlarmType> findTypes(AlarmTypeCriteria criteria) {
        return alarmManager.findTypes(criteria);
    }
    //endregion

    //region level
    public void createLevel(AlarmLevel level) {
        alarmManager.createLevel(level);
    }

    public void deleteLevelById(String levelId) {
        alarmManager.deleteLevelById(levelId);
    }

    public void updateLevelById(AlarmLevel level) {
        alarmManager.updateLevelById(level);
    }

    public Optional<AlarmLevel> findLevelById(String levelId) {
        return alarmManager.findLevelById(levelId);
    }

    public List<AlarmLevel> findLevels(AlarmLevelCriteria criteria) {
        return alarmManager.findLevels(criteria);
    }

    public void changeLevelEnabledById(AlarmLevel alarmLevel) {
        alarmManager.updateLevelById(alarmLevel);
    }

    //endregion

    //region alarm
    public void createAlarm(AlarmCreateArgBO alarmCreateArg) {
        ProductPDO productPDO = deviceProvider.findProductByKey(alarmCreateArg.getProductKey());
        if (productPDO == null) {
            return;
        }
        Map<String, ThingModelPropertyPDO> thingModelPropertiesMap = deviceProvider.findThingModelProperties(productPDO.getId(), productPDO.getThingModelVersion()).stream().collect(Collectors.toMap(ThingModelPropertyPDO::getIdentifier, Function.identity(), (v1, v2) -> v1));
        if (CollectionUtil.isNotEmpty(alarmCreateArg.getEventData())) {
            alarmCreateArg.getEventData().forEach(event -> {
                event.setLabel(Optional.ofNullable(thingModelPropertiesMap.get(event.getIdentifier())).map(ThingModelPropertyPDO::getName).orElse(null));
            });
        }
        Alarm alarm = Alarm.builder()
                .type(alarmCreateArg.getType())
                .level(alarmCreateArg.getLevel())
                .productKey(alarmCreateArg.getProductKey())
                .productName(productPDO.getName())
                .deviceName(alarmCreateArg.getDeviceName())
                .description(alarmCreateArg.getDescription())
                .eventData(alarmCreateArg.getEventData())
                .status(Alarm.Status.UN_PROCESSED)
                .build();
        alarmManager.createAlarm(alarm);
    }

    public void updateAlarmById(Alarm alarm) {
        alarmManager.updateAlarmById(alarm);
    }

    public void deleteAlarmById(String alarmId) {
        alarmManager.deleteAlarmById(alarmId);
    }

    public Optional<Alarm> findAlarmById(String alarmId) {
        return alarmManager.findAlarmById(alarmId);
    }

    /**
     * 分页查询警报信息。
     *
     * @param criteria 查询条件，包含分页和过滤条件。
     * @return PageInfo<Alarm> 返回警报信息的分页结果，包含当前页的警报列表和分页相关信息。
     */
    public PageInfo<Alarm> findAlarmsPage(AlarmCriteria criteria) {
        // 通过alarmManager实例执行分页查询
        return alarmManager.findAlarmsPage(criteria);
    }

    public PageInfo<AlarmDTO> findAlarmExtPage(AlarmCriteria criteria) {
        return alarmManager.findAlarmExtPage(criteria);
    }

    //endregion

}
