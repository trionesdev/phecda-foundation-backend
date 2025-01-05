package com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.internal;

import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmLog;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLogQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLogRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface AlarmRestConvert {
    AlarmRestConvert INSTANCE = Mappers.getMapper(AlarmRestConvert.class);

    AlarmLog from(AlarmLogRO args);

    AlarmLogCriteria from(AlarmLogQueryRO query);

}
