package com.trionesdev.phecda.backend.core.domains.alarm.internal;

import com.trionesdev.phecda.backend.core.domains.alarm.dao.entity.AlarmLog;
import com.trionesdev.phecda.backend.core.domains.alarm.service.bo.AlarmLogBO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))

public interface AlarmLogConvertMapper {
    AlarmLogConvertMapper INSTANCE = Mappers.getMapper(AlarmLogConvertMapper.class);

    List<AlarmLogBO> alarmLogBOFromRecord(List<AlarmLog> dictionaries);

    List<AlarmLogBO.ImageInfoBO> from(List<AlarmLog.ImageInfo> images);
}
