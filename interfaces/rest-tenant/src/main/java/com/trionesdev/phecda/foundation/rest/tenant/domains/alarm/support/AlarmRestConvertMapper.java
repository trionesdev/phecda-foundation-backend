package com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.support;

import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.AlarmLog;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.query.AlarmLogQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLogRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface AlarmRestConvertMapper {
    AlarmRestConvertMapper INSTANCE = Mappers.getMapper(AlarmRestConvertMapper.class);

    AlarmLog from(AlarmLogRO args);

    AlarmLogCriteria from(AlarmLogQuery query);

}
