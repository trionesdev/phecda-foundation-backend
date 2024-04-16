package ms.phecda.backend.core.domains.device.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.dvo.DeviceStatisticsDVO;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.impl.DeviceDAO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ms.phecda.backend.core.domains.device.internal.DeviceCacheConstants.DEVICE_NAME_NAMES;
import static ms.phecda.backend.core.domains.device.internal.DeviceCacheConstants.DEVICE_NAME_PREFIX;

@RequiredArgsConstructor
@Service
public class DeviceManager {
    private final DeviceDAO deviceDAO;

    public DeviceStatisticsDVO queryStatusStatistics() {
        return deviceDAO.selectStatusStatistics();
    }

    public void create(Device device) {
        deviceDAO.save(device);
    }

    public void deleteById(Device device) {
        deviceDAO.removeById(device);
    }

    public void updateById(Device device) {
        deviceDAO.updateById(device);
    }

    public void updateBatchById(List<Device> entityList) {
        deviceDAO.updateBatchById(entityList);
    }

    public Optional<Device> queryById(String id) {
        return Optional.ofNullable(deviceDAO.getById(id));
    }

    public PageInfo<Device> queryPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        return deviceDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<Device> queryList(DeviceCriteria criteria) {
        return deviceDAO.selectList(criteria);
    }

    public List<Device> listAll() {
        return deviceDAO.list();
    }

    @Cacheable(value = DEVICE_NAME_NAMES, key = "'" + DEVICE_NAME_PREFIX + "'+#name")
    public Optional<Device> queryByName(String name) {
        return deviceDAO.getByName(name);
    }


    public void removeChildDevice(String parentDeviceId, List<String> childDeviceIds) {
        deviceDAO.removeChildDevice(parentDeviceId, childDeviceIds);
    }

    @Caching(evict = {
            @CacheEvict(value = DEVICE_NAME_NAMES, key = "'" + DEVICE_NAME_PREFIX + "'+#device.name")
    })
    public void cleanDeviceCache(Device device) {
    }
}
