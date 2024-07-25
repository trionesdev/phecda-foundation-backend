package ms.phecda.backend.core.domains.device.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.dvo.DeviceStatisticsDVO;
import ms.phecda.backend.core.domains.device.dao.po.DevicePO;
import ms.phecda.backend.core.domains.device.dao.impl.DeviceDAO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ms.phecda.backend.core.domains.device.internal.DeviceCacheConstants.*;

@RequiredArgsConstructor
@Service
public class DeviceManager {
    private final DeviceDAO deviceDAO;

    public DeviceStatisticsDVO queryStatusStatistics() {
        return deviceDAO.selectStatusStatistics();
    }

    public void create(DevicePO device) {
        deviceDAO.save(device);
    }

    public void deleteById(DevicePO device) {
        deviceDAO.removeById(device);
    }

    public void updateById(DevicePO device) {
        deviceDAO.updateById(device);
    }

    public void updateBatchById(List<DevicePO> entityList) {
        deviceDAO.updateBatchById(entityList);
    }

    public Optional<DevicePO> queryById(String id) {
        return Optional.ofNullable(deviceDAO.getById(id));
    }

    public PageInfo<DevicePO> queryPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        return deviceDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<DevicePO> queryList(DeviceCriteria criteria) {
        return deviceDAO.selectList(criteria);
    }

    public List<DevicePO> listAll() {
        return deviceDAO.list();
    }

    @Cacheable(value = DEVICE_NAMES, key = "'" + DEVICE_NAME_PREFIX + "'+#name")
    public Optional<DevicePO> queryByName(String name) {
        return deviceDAO.getByName(name);
    }


    public void removeChildDevice(String parentDeviceId, List<String> childDeviceIds) {
        deviceDAO.removeChildDevice(parentDeviceId, childDeviceIds);
    }

    @Caching(evict = {
            @CacheEvict(value = DEVICE_NAMES, key = "'" + DEVICE_NAME_PREFIX + "'+#device.name")
    })
    public void cleanDeviceCache(DevicePO device) {
    }
}
