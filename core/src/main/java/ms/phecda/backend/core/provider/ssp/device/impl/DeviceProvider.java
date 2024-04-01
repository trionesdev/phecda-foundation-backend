package ms.phecda.backend.core.provider.ssp.device.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.service.bo.DeviceCriteriaBO;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.domains.device.service.impl.ProductService;
import ms.phecda.backend.core.domains.device.support.DeviceConvertMapper;
import ms.phecda.backend.core.provider.ssp.device.DeviceProviderConvert;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.ProductPDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.thingmodel.ThingModelPropertyPDO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DeviceProvider {
    private final DeviceProviderConvert deviceProviderConvert;
    private final ProductService productService;

    private final DeviceService deviceService;

    public ProductPDO findProductByKey(String key) {
        Product product = productService.findProductByKey(key).orElse(null);
        return deviceProviderConvert.from(product);
    }

    public List<ThingModelPropertyPDO> findThingModelProperties(String productId, String version) {
        return productService.queryThingModelCache(productId, version).map(thingModelVersion -> {
            return deviceProviderConvert.thisModelPropertiesPDOFromModel(thingModelVersion.getThingModel().getProperties());
        }).orElse(Collections.emptyList());
    }

    public List<DevicePDO> listById(List<String> ids) {
        DeviceCriteriaBO criteria = DeviceCriteriaBO.builder().ids(ids).build();
        List<Device> devices = deviceService.queryList(criteria);
        return DeviceConvertMapper.INSTANCE.toPDOList(devices);
    }

    public DevicePDO queryByName(String name) {
        Optional<Device> deviceOptional = deviceService.queryByName(name);
        return DeviceConvertMapper.INSTANCE.toPDO(deviceOptional.orElse(null));
    }
}
