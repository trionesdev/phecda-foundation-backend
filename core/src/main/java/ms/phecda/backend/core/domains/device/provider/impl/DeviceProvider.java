package ms.phecda.backend.core.domains.device.provider.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dto.DeviceDTO;
import ms.phecda.backend.core.domains.device.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.internal.DeviceBeanConvert;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.manager.impl.DeviceManager;
import ms.phecda.backend.core.domains.device.manager.impl.ProductManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DeviceProvider {
    private final DeviceBeanConvert convert;
    private final ProductManager productManager;

    private final DeviceManager deviceManager;

    public List<ProductDTO> findProductsByKeys(Collection<String> keys){
        return productManager.findProductsByKeys(keys).stream().map(convert::poToDto).collect(Collectors.toList());
    }

    public ProductDTO findProductByKey(String key) {
        return productManager.findByKey(key).map(convert::productEntityToDto).orElse(null);
    }


    public ThingModelProperty findThingModelPropertyByKey(String productKey, String identifier) {
        return productManager.findLatestThingModelPropertyByProductKey(productKey, identifier).orElse(null);
    }


    public DeviceDTO queryByName(String name) {
        return deviceManager.queryByName(name).map(convert::devicePoToDto).orElse(null);
    }


}
