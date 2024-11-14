package com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.support;

import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.AlarmLevel;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.AlarmType;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.query.AlarmLevelQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.query.AlarmQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.query.AlarmTypeQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLevelCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLevelUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmTypeCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmTypeUpdateRO;
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
