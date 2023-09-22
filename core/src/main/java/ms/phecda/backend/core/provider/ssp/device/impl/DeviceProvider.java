package ms.phecda.backend.core.provider.ssp.device.impl;

import ms.phecda.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.modules.device.dao.entity.Device;
import ms.phecda.backend.core.modules.device.service.impl.DeviceService;
import ms.phecda.backend.core.modules.device.support.DeviceConvertMapper;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class DeviceProvider {
    @Lazy
    @Resource
    private DeviceService deviceService;

    public List<DevicePDO> listById(List<String> ids) {
        DeviceCriteria criteria = DeviceCriteria.builder().ids(ids).build();
        List<Device> devices = deviceService.queryList(criteria);
        return DeviceConvertMapper.INSTANCE.toPDOList(devices);
    }

    public DevicePDO queryByName(String name) {
        Optional<Device> deviceOptional = deviceService.queryByName(name);
        return DeviceConvertMapper.INSTANCE.toPDO(deviceOptional.orElse(null));
    }
}
