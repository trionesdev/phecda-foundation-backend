package com.trionesdev.phecda.foundation.core.domains.device.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.exception.NotFoundException;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceStatisticsMessageDailyCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.*;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.*;
import com.trionesdev.phecda.foundation.core.domains.device.dto.DevicePropertyDataDTO;
import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbSave;
import com.trionesdev.phecda.infrastructure.tsdb.TsDbTemplate;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbCell;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DeviceDataManager {
    private final DeviceEventLogDAO deviceEventLogDAO;
    private final DeviceServiceLogDAO deviceServiceLogDAO;
    private final DevicePropertyDataDAO devicePropertyDataDAO;
    private final DeviceDAO deviceDAO;
    private final ProductDAO productDAO;
    private final DeviceStatisticsMessageDailyDAO deviceStatisticsMessageDailyDAO;
    private final TsDbTemplate tsDbTemplate;


    public PageInfo<DeviceEventLogPO> eventLogsPage(DeviceEventLogCriteria criteria) {
        Page<DeviceEventLogPO> page = deviceEventLogDAO.page(criteria);
        return MpPageUtils.of(page);
    }


    public PageInfo<DeviceCommandLogPO> serviceLogsPage(DeviceServiceLogCriteria criteria) {
        Page<DeviceCommandLogPO> page = deviceServiceLogDAO.page(criteria);

        return MpPageUtils.of(page);
    }

    public void saveServiceLog(DeviceCommandLogPO entity) {
        deviceServiceLogDAO.save(entity);
    }

    public void updateServiceLog(DeviceCommandLogPO entity) {
        deviceServiceLogDAO.updateById(entity);
    }

    //region device property data
    public void savePropertyData(IotDbSave data) {
        devicePropertyDataDAO.saveBatch(data);
    }

    public ProductPO getProductByDeviceName(String deviceName) {
        DevicePO device = deviceDAO.getByName(deviceName).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productDAO.getById(device.getProductId());
    }

    public List<List<TsDbCell>> queryDevicePropertyDataList(DevicePropertyDataCriteria criteria) {
        ProductPO product = getProductByDeviceName(criteria.getDeviceName());
        criteria.setProductKey(product.getKey());
        return devicePropertyDataDAO.selectList(criteria);
    }

    public Map<String,Object> queryDevicePropertyLastData(String deviceName, List<String> fields) {
        ProductPO product = getProductByDeviceName(deviceName);
        var query = DevicePropertyDataCriteria.builder().productKey(product.getKey()).deviceName(deviceName).identifiers(fields).limit(1).build();
        var data = devicePropertyDataDAO.selectLastList(query);
        var map = new HashMap<String, Object>();
        if (CollectionUtils.isNotEmpty(data)) {
            data.forEach(property -> {
                map.put(property.getColumnName(), property.getValue());
            });
            return map;
        }
        return new HashMap<>();
    }

//    public List<DevicePropertyDataDTO> queryDevicePropertyDataList(DevicePropertyDataCriteria criteria) {
//        List<List<TsDbCell>> rawsData = queryDevicePropertyDataList(criteria);
//        return assembleDevicePropertiesData(rawsData, criteria);
//    }

    public List<DevicePropertyDataDTO> assembleDevicePropertiesData(List<List<TsDbCell>> rawsData, DevicePropertyDataCriteria criteria) {
        if (CollectionUtil.isEmpty(rawsData)) {
            return Collections.emptyList();
        }
        ProductPO product = getProductByDeviceName(criteria.getDeviceName());

        return Collections.emptyList();
//        return rawsData.stream().map(row -> {
//            String timeStr = String.valueOf(row.get("Time")).substring(0, 13);
//            Instant time = Instant.ofEpochMilli(Long.parseLong(timeStr));
//            String devicePath = IotDbUtils.generatePath(Lists.newArrayList(product.getKey(), criteria.getDeviceName(), criteria.getIdentifier()));
//            Object value = row.get(devicePath);
//            return DevicePropertyDataDTO.builder()
//                    .ts(time)
//                    .value(value)
//                    .identifier(criteria.getIdentifier())
//                    .deviceName(criteria.getDeviceName())
//                    .build();
//        }).collect(Collectors.toList());
    }

    //endregion

    public void saveStatisticsMessageDaily(DeviceStatisticsMessageDailyPO record) {
        deviceStatisticsMessageDailyDAO.save(record);
    }

    public List<DeviceStatisticsMessageDailyPO> findList(DeviceStatisticsMessageDailyCriteria criteria) {
        return deviceStatisticsMessageDailyDAO.selectList(criteria);
    }

    public Long getQuantitySum(DeviceStatisticsMessageDailyCriteria criteria) {
        return deviceStatisticsMessageDailyDAO.selectQuantitySum(criteria);
    }

}
