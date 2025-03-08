package com.trionesdev.phecda.foundation.rest.tenant.domains.device.internal;

import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductDriverCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DriverPO;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductThingModelUpsertCmd;
import com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DeviceEventLogQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DevicePropertyDataQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DeviceQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DeviceCommandLogQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.ProductDriverQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.ProductQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.*;
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
    Product from(ProductCreateRO args);

    Product from(ProductUpdateRO args);

    ProductCriteria from(ProductQueryRO query);

//    ThingModelUpsertBO from(ProductThingModelUpsertRO args);
    ProductThingModelUpsertCmd from(ProductThingModelUpsertRO args);
    //endregion

    DevicePO from(DeviceCreateRO args);

    DevicePO from(DeviceUpdateRO args);

    DeviceCriteria from(DeviceQueryRO query);

    DevicePropertyDataCriteria from(DevicePropertyDataQueryRO query);

    DeviceEventLogCriteria from(DeviceEventLogQueryRO query);

    DeviceServiceLogCriteria from(DeviceCommandLogQueryRO query);


    //region product driver
    DriverPO from(DriverRO.Create args);

    DriverPO from(DriverRO.Update args);

    ProductDriverCriteria from(ProductDriverQueryRO query);
    //endregion

}
