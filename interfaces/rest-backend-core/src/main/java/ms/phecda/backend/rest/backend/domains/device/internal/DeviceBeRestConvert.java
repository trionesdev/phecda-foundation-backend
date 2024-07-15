package ms.phecda.backend.rest.backend.domains.device.internal;

import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductDriverCriteria;
import ms.phecda.backend.core.domains.device.dao.po.DevicePO;
import ms.phecda.backend.core.domains.device.dao.po.DriverPO;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO;
import ms.phecda.backend.core.domains.device.dto.ProductThingModelUpsertCmd;
import ms.phecda.backend.core.domains.device.service.bo.ThingModelUpsertBO;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DeviceEventLogQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DevicePropertyDataQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DeviceQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.DeviceServiceLogQuery;
import ms.phecda.backend.rest.backend.domains.device.controller.query.ProductDriverQuery;
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

    //region product
    ProductPO from(ProductRO.Create args);

    ProductPO from(ProductRO.Update args);

    ProductCriteria from(ProductQuery query);

//    ThingModelUpsertBO from(ProductThingModelUpsertRO args);
    ProductThingModelUpsertCmd from(ProductThingModelUpsertRO args);
    //endregion

    DevicePO from(DeviceCreateRO args);

    DevicePO from(DeviceUpdateRO args);

    DeviceCriteria from(DeviceQuery query);

    DevicePropertyDataCriteria from(DevicePropertyDataQuery query);

    DeviceEventLogCriteria from(DeviceEventLogQuery query);

    DeviceServiceLogCriteria from(DeviceServiceLogQuery query);


    //region product driver
    DriverPO from(DriverRO.Create args);

    DriverPO from(DriverRO.Update args);

    ProductDriverCriteria from(ProductDriverQuery query);
    //endregion

}
