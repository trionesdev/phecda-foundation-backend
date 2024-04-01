package ms.phecda.backend.rest.backend.domains.device.support;

import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.service.bo.DeviceCriteriaBO;
import ms.phecda.backend.core.domains.device.service.bo.ThingModelUpsertBO;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DeviceQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.ProductQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.ro.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceRestConvertMapper {
    DeviceRestConvertMapper INSTANT = Mappers.getMapper(DeviceRestConvertMapper.class);

    Product from(ProductCreateRO args);

    Product from(ProductUpdateRO args);

    ProductCriteria from(ProductQuery query);

    ThingModelUpsertBO from(ProductThingModelUpsertRO args);

    Device from(DeviceCreateRO args);

    Device from(DeviceUpdateRO args);

    DeviceCriteriaBO from(DeviceQuery query);
}
