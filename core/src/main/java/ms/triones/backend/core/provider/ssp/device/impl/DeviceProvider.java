package ms.triones.backend.core.provider.ssp.device.impl;

import ms.triones.backend.core.modules.device.service.impl.DeviceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DeviceProvider {
    @Lazy
    @Resource
    private DeviceService deviceService;

    public String getNodeIdByName(String name) {
        return deviceService.getNodeIdByName(name);
    }
}
