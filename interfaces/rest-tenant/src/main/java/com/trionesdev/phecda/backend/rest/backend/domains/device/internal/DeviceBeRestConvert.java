package com.trionesdev.phecda.backend.rest.backend.domains.device.internal;

import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.ProductDriverCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DriverPO;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductDTO;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductThingModelUpsertCmd;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DeviceEventLogQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DevicePropertyDataQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DeviceQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DeviceServiceLogQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.ProductDriverQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.ProductQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro.*;
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
    ProductDTO from(ProductRO.Create args);

    ProductDTO from(ProductRO.Update args);

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
