package ms.phecda.backend.rest.backend.modules.device.support;

import ms.phecda.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.modules.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.modules.device.dao.entity.Device;
import ms.phecda.backend.core.modules.device.dao.entity.Product;
import ms.phecda.backend.core.modules.device.service.bo.ThingModelUpsertBO;
import ms.phecda.backend.rest.backend.modules.device.controller.query.DeviceQuery;
import ms.phecda.backend.rest.backend.modules.device.controller.query.ProductQuery;
import ms.phecda.backend.rest.backend.modules.device.controller.ro.DeviceCreateRO;
import ms.phecda.backend.rest.backend.modules.device.controller.ro.DeviceUpdateRO;
import ms.phecda.backend.rest.backend.modules.device.controller.ro.ProductCreateRO;
import ms.phecda.backend.rest.backend.modules.device.controller.ro.ProductThingModelUpsertRO;
import ms.phecda.backend.rest.backend.modules.device.controller.ro.ProductUpdateRO;
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

    DeviceCriteria from(DeviceQuery query);
}
