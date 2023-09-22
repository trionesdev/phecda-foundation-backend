package ms.phecda.backend.rest.backend.modules.alarm.support;

import ms.phecda.backend.core.modules.alarm.dao.criteria.AlarmLogCriteria;
import ms.phecda.backend.core.modules.alarm.dao.entity.AlarmLog;
import ms.phecda.backend.rest.backend.modules.alarm.controller.query.AlarmLogQuery;
import ms.phecda.backend.rest.backend.modules.alarm.controller.ro.AlarmLogRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface AlarmRestConvertMapper {
    AlarmRestConvertMapper INSTANCE = Mappers.getMapper(AlarmRestConvertMapper.class);

    AlarmLog from(AlarmLogRO args);

    AlarmLogCriteria from(AlarmLogQuery query);

}
