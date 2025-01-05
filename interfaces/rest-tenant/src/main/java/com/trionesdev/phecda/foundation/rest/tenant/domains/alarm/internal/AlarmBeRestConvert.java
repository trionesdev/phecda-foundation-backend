package com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.internal;

import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmLevelCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmTypeCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmLevelPO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmTypePO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLevelQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmTypeQueryRO;
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

    AlarmTypePO from(AlarmTypeCreateRO args);

    AlarmTypePO from(AlarmTypeUpdateRO args);

    AlarmTypeCriteria from(AlarmTypeQueryRO query);

    AlarmLevelPO from(AlarmLevelCreateRO args);

    AlarmLevelPO from(AlarmLevelUpdateRO args);

    AlarmLevelCriteria from(AlarmLevelQueryRO query);

    AlarmCriteria from(AlarmQueryRO query);
}
