package ms.triones.backend.rest.backend.modules.asset.support;

import ms.triones.backend.core.modules.asset.dao.criteria.SparePartCriteria;
import ms.triones.backend.core.modules.asset.dao.entity.SparePart;
import ms.triones.backend.rest.backend.modules.asset.controller.query.SparePartQuery;
import ms.triones.backend.rest.backend.modules.asset.controller.ro.AssetRO;
import ms.triones.backend.rest.backend.modules.asset.controller.ro.SparePartRO;
import ms.triones.backend.core.modules.asset.dao.criteria.AssetCriteria;
import ms.triones.backend.core.modules.asset.dao.entity.Asset;
import ms.triones.backend.rest.backend.modules.asset.controller.query.AssetQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface AssetRestConvertMapper {
    AssetRestConvertMapper INSTANT = Mappers.getMapper(AssetRestConvertMapper.class);

    SparePart from(SparePartRO args);

    SparePartCriteria from(SparePartQuery query);

    AssetCriteria from(AssetQuery query);

    Asset from(AssetRO args);
}
