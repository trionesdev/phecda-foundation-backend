package ms.phecda.backend.rest.backend.domains.device.support;

import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.service.bo.ThingModelUpsertBO;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DeviceEventLogQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DeviceQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DeviceServiceLogQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.ProductQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.ro.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("deviceBeRestConvert")
public interface DeviceBeRestConvert {
    DeviceBeRestConvert INSTANT = Mappers.getMapper(DeviceBeRestConvert.class);

    Product from(ProductCreateRO args);

    Product from(ProductUpdateRO args);

    ProductCriteria from(ProductQuery query);

    ThingModelUpsertBO from(ProductThingModelUpsertRO args);

    Device from(DeviceCreateRO args);

    Device from(DeviceUpdateRO args);

    DeviceCriteria from(DeviceQuery query);

    DeviceEventLogCriteria from(DeviceEventLogQuery query);

    DeviceServiceLogCriteria from(DeviceServiceLogQuery query);
}
