package ms.phecda.backend.core.domains.device.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.exception.NotFoundException;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceEventLog;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.impl.DeviceDAO;
import ms.phecda.backend.core.domains.device.dao.impl.DeviceEventLogDAO;
import ms.phecda.backend.core.domains.device.dao.impl.DevicePropertyDataDAO;
import ms.phecda.backend.core.domains.device.dao.impl.DeviceServiceLogDAO;
import ms.phecda.backend.core.domains.device.internal.util.IotDbUtils;
import ms.phecda.backend.core.domains.device.manager.dto.DevicePropertyDataDTO;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceDataManager {
    private final DeviceEventLogDAO deviceEventLogDAO;
    private final DeviceServiceLogDAO deviceServiceLogDAO;
    private final DevicePropertyDataDAO devicePropertyDataDAO;
    private final DeviceDAO deviceDAO;
    private final ProductManager productManager;

    public PageInfo<DeviceEventLog> eventLogsPage(DeviceEventLogCriteria criteria) {
        Page<DeviceEventLog> page = deviceEventLogDAO.page(criteria);
        return MpPageUtils.of(page);
    }


    public PageInfo<DeviceServiceLog> serviceLogsPage(DeviceServiceLogCriteria criteria) {
        Page<DeviceServiceLog> page = deviceServiceLogDAO.page(criteria);

        return MpPageUtils.of(page);
    }

    public void saveServiceLog(DeviceServiceLog entity) {
        deviceServiceLogDAO.save(entity);
    }

    //region device property data
    public void savePropertyData(String productKey, String deviceName, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
        devicePropertyDataDAO.insertRecord(IotDbUtils.generatePath(Lists.newArrayList(productKey, deviceName)), time, measurements, types, values);
    }

    public Product getProductByDeviceName(String deviceName) {
        Device device = deviceDAO.getByName(deviceName).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productManager.queryById(device.getProductId()).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
    }

    public List<Map<String, Object>> queryDevicePropertyDataList(String deviceName, List<String> fields, long startTime, long endTime) {
        Product product = getProductByDeviceName(deviceName);
        List<String> paths = fields.stream().map(field -> IotDbUtils.generatePath(Lists.newArrayList(product.getKey(), deviceName, field))).collect(Collectors.toList());
        return devicePropertyDataDAO.selectList(paths, startTime, endTime);
    }

    public List<Map<String, Object>> queryDevicePropertyLastData(String deviceName, List<String> fields) {
        Product product = getProductByDeviceName(deviceName);
        List<String> paths = fields.stream().map(field -> IotDbUtils.generatePath(Lists.newArrayList(product.getKey(), deviceName, field))).collect(Collectors.toList());
        return devicePropertyDataDAO.selectLastList(paths);
    }

    public List<DevicePropertyDataDTO> queryDevicePropertyDataList(DevicePropertyDataCriteria criteria) {
        List<Map<String, Object>> rawsData = queryDevicePropertyDataList(criteria.getDeviceName(), Lists.newArrayList(criteria.getIdentifier()),
                Optional.of(criteria.getStartTime()).map(Instant::toEpochMilli).get(), Optional.of(criteria.getStartTime()).map(Instant::toEpochMilli).orElse(null));
        return assembleDevicePropertiesData(rawsData, criteria);
    }

    public List<DevicePropertyDataDTO> assembleDevicePropertiesData(List<Map<String, Object>> rawsData, DevicePropertyDataCriteria criteria) {
        if (CollectionUtil.isEmpty(rawsData)) {
            return Collections.emptyList();
        }
        Product product = getProductByDeviceName(criteria.getDeviceName());

        return rawsData.stream().map(row -> {
            String timeStr = String.valueOf(row.get("Time")).substring(0, 13);
            Instant time = Instant.ofEpochMilli(Long.parseLong(timeStr));
            String devicePath = IotDbUtils.generatePath(Lists.newArrayList(product.getKey(), criteria.getDeviceName(), criteria.getIdentifier()));
            Object value = row.get(devicePath);
            return DevicePropertyDataDTO.builder()
                    .ts(time)
                    .value(value)
                    .identifier(criteria.getIdentifier())
                    .deviceName(criteria.getDeviceName())
                    .build();
        }).collect(Collectors.toList());
    }

    //endregion

}
