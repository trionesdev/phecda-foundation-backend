package ms.triones.backend.core.modules.device.manager.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.impl.DeviceDAO;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceManager {
    private final DeviceDAO deviceDAO;

    public void create(Device device) {
        deviceDAO.save(device);
    }

    public void deleteById(String id) {
        deviceDAO.removeById(id);
    }

    public void updateById(Device device) {
        deviceDAO.updateById(device);
    }

    public PageInfo<Device> queryPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        return deviceDAO.selectPage(pageNum, pageSize, criteria);
    }


}
