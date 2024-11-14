package com.trionesdev.phecda.foundation.core.domains.alarm.internal;

import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.Alarm;
import com.trionesdev.phecda.foundation.core.domains.alarm.manager.dto.AlarmDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("alarmConvert")
public interface AlarmConvert {
    AlarmDTO from(Alarm alarm);
}
