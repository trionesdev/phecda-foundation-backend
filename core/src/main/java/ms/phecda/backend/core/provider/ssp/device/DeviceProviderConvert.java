package ms.phecda.backend.core.provider.ssp.device;

import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelProperty;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.ProductPDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.thingmodel.ThingModelPropertyPDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("deviceProviderConvert")
public interface DeviceProviderConvert {
    ProductPDO from(Product product);

    DevicePDO from(Device device);

    List<ThingModelPropertyPDO> thisModelPropertiesPDOFromModel(List<ThingModelProperty> thingModelProperties);
}
