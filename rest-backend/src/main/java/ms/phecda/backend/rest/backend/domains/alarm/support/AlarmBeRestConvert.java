package ms.phecda.backend.rest.backend.domains.alarm.support;

import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmCriteria;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmLevel;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmType;
import ms.phecda.backend.rest.backend.domains.alarm.controller.query.AlarmLevelQuery;
import ms.phecda.backend.rest.backend.domains.alarm.controller.query.AlarmQuery;
import ms.phecda.backend.rest.backend.domains.alarm.controller.query.AlarmTypeQuery;
import ms.phecda.backend.rest.backend.domains.alarm.controller.ro.AlarmLevelCreateRO;
import ms.phecda.backend.rest.backend.domains.alarm.controller.ro.AlarmLevelUpdateRO;
import ms.phecda.backend.rest.backend.domains.alarm.controller.ro.AlarmTypeCreateRO;
import ms.phecda.backend.rest.backend.domains.alarm.controller.ro.AlarmTypeUpdateRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("alarmBeRestConvert")
public interface AlarmBeRestConvert {

    AlarmType from(AlarmTypeCreateRO args);

    AlarmType from(AlarmTypeUpdateRO args);

    AlarmTypeCriteria from(AlarmTypeQuery query);

    AlarmLevel from(AlarmLevelCreateRO args);

    AlarmLevel from(AlarmLevelUpdateRO args);

    AlarmLevelCriteria from(AlarmLevelQuery query);

    AlarmCriteria from(AlarmQuery query);
}
