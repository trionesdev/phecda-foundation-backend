package ms.triones.backend.rest.backend.modules.alarm.support;

import ms.triones.backend.core.modules.alarm.dao.criteria.AlarmLogCriteria;
import ms.triones.backend.core.modules.alarm.dao.entity.AlarmLog;
import ms.triones.backend.core.modules.asset.dao.criteria.AssetCriteria;
import ms.triones.backend.core.modules.asset.dao.criteria.SparePartCriteria;
import ms.triones.backend.core.modules.asset.dao.entity.Asset;
import ms.triones.backend.core.modules.asset.dao.entity.SparePart;
import ms.triones.backend.rest.backend.modules.alarm.controller.query.AlarmLogQuery;
import ms.triones.backend.rest.backend.modules.alarm.controller.ro.AlarmLogRO;
import ms.triones.backend.rest.backend.modules.asset.controller.query.AssetQuery;
import ms.triones.backend.rest.backend.modules.asset.controller.query.SparePartQuery;
import ms.triones.backend.rest.backend.modules.asset.controller.ro.AssetRO;
import ms.triones.backend.rest.backend.modules.asset.controller.ro.SparePartRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface AlarmRestConvertMapper {
    AlarmRestConvertMapper INSTANCE = Mappers.getMapper(AlarmRestConvertMapper.class);

    AlarmLog from(AlarmLogRO args);

    AlarmLogCriteria from(AlarmLogQuery query);

}
