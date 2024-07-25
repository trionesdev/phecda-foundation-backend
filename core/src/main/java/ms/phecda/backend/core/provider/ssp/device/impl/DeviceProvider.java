package ms.phecda.backend.core.provider.ssp.device.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.internal.DeviceBeanConvert;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.po.DevicePO;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO;
import ms.phecda.backend.core.domains.device.service.bo.InvokeServiceArgBO;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.domains.device.service.impl.ProductService;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.dto.ProductDTO;
import ms.phecda.backend.core.provider.ssp.device.DeviceProviderConvert;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.ProductPDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.InvokeServiceArgPDO;
import ms.phecda.backend.core.provider.ssp.device.pdo.thingmodel.ThingModelPropertyPDO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DeviceProvider {
    private final DeviceProviderConvert deviceProviderConvert;
    private final ProductService productService;

    private final DeviceService deviceService;

    public List<ProductDTO> findProductsByKeys(Collection<String> keys){
        return productService.findProductsByKeys(keys);
    }

    public ProductDTO findProductByKey(String key) {
        return productService.findProductByKey(key).orElse(null);
    }

    public List<ThingModelPropertyPDO> findThingModelPropertiesByKey(String productKey) {
        List<ThingModelProperty> properties = productService.queryThingModelLatestPropertiesByProductKey(productKey);
        return deviceProviderConvert.thisModelPropertiesPDOFromModel(properties);
    }

    public ThingModelPropertyPDO findThingModelPropertyByKey(String productKey, String identifier) {
        return productService.queryThingModelLatestProperty(productKey, identifier).map(deviceProviderConvert::from).orElse(null);
    }


    public List<DevicePDO> listById(List<String> ids) {
        DeviceCriteria criteria = DeviceCriteria.builder().ids(ids).build();
        List<DevicePO> devices = deviceService.queryList(criteria);
        return DeviceBeanConvert.INSTANCE.toPDOList(devices);
    }

    public DevicePDO queryByName(String name) {
        Optional<DevicePO> deviceOptional = deviceService.queryByName(name);
        return DeviceBeanConvert.INSTANCE.toPDO(deviceOptional.orElse(null));
    }

    public void invokeService(InvokeServiceArgPDO args) {
        InvokeServiceArgBO invokeServiceArgBO = InvokeServiceArgBO.builder()
                .identifier(args.getServiceIdentifier())
                .params(args.getParams())
                .body(args.getBody())
                .tags(args.getTags())
                .build();
        deviceService.invokeService(args.getProductKey(), args.getDeviceName(), invokeServiceArgBO);
    }

}
