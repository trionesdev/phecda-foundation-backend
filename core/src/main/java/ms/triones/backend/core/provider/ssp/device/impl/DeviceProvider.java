package ms.triones.backend.core.provider.ssp.device.impl;

import ms.triones.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.service.impl.DeviceService;
import ms.triones.backend.core.modules.device.support.DeviceConvertMapper;
import ms.triones.backend.core.provider.ssp.device.pdo.DevicePDO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

    public void updateNodeIdOfDevice(String nodeId, List<String> ids) {
        deviceService.updateNodeIdOfDevice(nodeId, ids);
    }

    public void removeNodeIdOfDevice(String nodeId, List<String> ids) {
        deviceService.removeNodeIdOfDevice(nodeId, ids);
    }
}
