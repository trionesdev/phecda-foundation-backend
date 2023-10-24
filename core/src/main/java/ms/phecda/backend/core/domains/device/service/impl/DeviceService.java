package ms.phecda.backend.core.domains.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.core.util.PageUtils;
import com.moensun.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.manager.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.manager.impl.DeviceManager;
import ms.phecda.backend.core.domains.device.manager.impl.ProductManager;
import ms.phecda.backend.core.domains.device.service.bo.DeviceEventDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceExtBO;
import ms.phecda.backend.core.domains.device.service.bo.DevicePropertyDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceServiceDataBO;
import ms.phecda.backend.core.domains.device.support.DeviceConvertMapper;
import ms.phecda.backend.core.messageaccess.DeviceThingModelEventPublisher;
import ms.phecda.backend.core.messageaccess.event.DeviceDisableEvent;
import ms.phecda.backend.core.messageaccess.event.DeviceEnableEvent;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessage;
import ms.phecda.backend.core.provider.ssp.edge.impl.NodeDeviceProvider;
import ms.phecda.backend.core.provider.ssp.edge.pdo.NodeDevicePDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceManager deviceManager;
    private final ProductManager productManager;
    private final NodeDeviceProvider nodeDeviceProvider;
    private final ApplicationEventPublisher eventPublisher;
    private final DeviceThingModelEventPublisher deviceThingModelEventPublisher;

    @Value("${streaming.media.host}")
    private String streamingMediaHost = "127.0.0.1";
    @Value("${streaming.media.rtsp.port}")
    private Integer streamingMediaRtspPort = 554;
    @Value("${streaming.media.http.port}")
    private Integer streamingMediaHttpPort = 80;

    private static final String RTSP_URL = "rtsp://{host}:{port}/{productId}/{deviceName}";
    private static final String FLV_URL = "http://{host}:{port}/{productId}/{deviceName}.live.flv";


    public void createDevice(Device device) {
        deviceManager.create(device);
    }

    public void deleteDeviceById(String id) {
        deviceManager.deleteById(id);
    }

    public void updateById(Device device) {
        deviceManager.updateById(device);
    }

    public Optional<DeviceExtBO> queryExtById(String id) {
        return deviceManager.queryById(id).map(t -> {
            DeviceExtBO deviceExt = DeviceConvertMapper.INSTANCE.from(t);
            productManager.queryExtById(t.getProductId()).ifPresent(deviceExt::setProduct);
            return deviceExt;
        });
    }

    public PageInfo<DeviceExtBO> queryExtPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getNodeId())) {
            List<NodeDevicePDO> nodeDevicePDOS = nodeDeviceProvider.listByNodeId(criteria.getNodeId());
            Set<String> deviceIds = nodeDevicePDOS.stream()
                    .map(NodeDevicePDO::getDeviceId)
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(deviceIds)) {
                criteria.setIds(deviceIds);
            } else {
                criteria.setIds(Lists.newArrayList(String.valueOf(Long.MIN_VALUE)));
            }
            criteria.setNodeId(null);
        }

        PageInfo<Device> pageInfo = deviceManager.queryPage(pageNum, pageSize, criteria);
        if (CollectionUtil.isEmpty(pageInfo.getRows())) {
            return PageUtils.empty();
        }
        Set<String> productIds = pageInfo.getRows().stream().map(Device::getProductId).collect(Collectors.toSet());
        Map<String, ProductDTO> productMap = productManager.queryAllByIds(productIds).stream().collect(Collectors.toMap(Product::getId, v -> v, (v1, v2) -> v1));
        List<DeviceExtBO> deviceExtList = pageInfo.getRows().stream().map(t -> {
            DeviceExtBO deviceExt = DeviceConvertMapper.INSTANCE.from(t);
            deviceExt.setProduct(productMap.get(t.getProductId()));
            return deviceExt;
        }).collect(Collectors.toList());
        return PageUtils.of(pageInfo, deviceExtList);
    }

    /**
     * 设备上线
     *
     * @param deviceId
     */
    @Transactional
    public void deviceOnline(String deviceId) {
        Device device = deviceManager.queryById(deviceId)
                .orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));

        deviceManager.updateById(Device.builder()
                .id(deviceId)
                .enabled(true)
                .build());

        eventPublisher.publishEvent(DeviceEnableEvent.build(device));
    }

    /**
     * 设备下线
     *
     * @param deviceId
     */
    @Transactional
    public void deviceOffline(String deviceId) {
        Device device = deviceManager.queryById(deviceId)
                .orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));

        deviceManager.updateById(Device.builder()
                .id(deviceId)
                .enabled(false)
                .build());

        eventPublisher.publishEvent(DeviceDisableEvent.build(device));
    }

    public List<DevicePropertyDataBO> queryDeviceThingModelPropertiesData(String deviceId) {
        Device device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productManager.findThingModel(device.getProductId()).map(thingModel -> {
            return thingModel.getProperties().stream().map(property -> {
                DevicePropertyDataBO devicePropertyData = DeviceConvertMapper.INSTANCE.from(property);
                return devicePropertyData;
            }).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }

    public List<DeviceEventDataBO> queryDeviceThingModelEventsData(String deviceId) {
        Device device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productManager.findThingModel(device.getProductId()).map(thingModel -> {
            return thingModel.getEvents().stream().map(property -> {
                DeviceEventDataBO deviceEventData = DeviceConvertMapper.INSTANCE.from(property);
                return deviceEventData;
            }).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }

    public List<DeviceServiceDataBO> queryDeviceThingModelServicesData(String deviceId) {
        Device device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productManager.findThingModel(device.getProductId()).map(thingModel -> {
            return thingModel.getServices().stream().map(property -> {
                DeviceServiceDataBO deviceServiceData = DeviceConvertMapper.INSTANCE.from(property);
                return deviceServiceData;
            }).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }

    public List<Device> queryAllDevice() {
        return deviceManager.listAll();
    }

    public List<Device> queryNoRelationDevice(String assetSn) {
        return Collections.emptyList();
    }

    public List<Device> queryAssetRelationDevice(String assetSn) {
        return Collections.emptyList();
    }

    public List<DevicePropertyDataBO> queryDeviceProperties(String deviceName) {
        Optional<Device> deviceOptional = deviceManager.queryByName(deviceName);
        Device device = deviceOptional.orElse(Device.builder().build());
        return productManager.findThingModel(device.getProductId()).map(thingModel ->
                thingModel.getProperties().stream().map(property -> DeviceConvertMapper.INSTANCE.from(property))
                        .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public List<Device> queryList(DeviceCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getNodeId())) {
            List<NodeDevicePDO> nodeDevicePDOS = nodeDeviceProvider.listByNodeId(criteria.getNodeId());
            Set<String> deviceIds = nodeDevicePDOS.stream()
                    .map(NodeDevicePDO::getDeviceId)
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(deviceIds)) {
                criteria.setIds(deviceIds);
            } else {
                criteria.setIds(Lists.newArrayList(String.valueOf(Long.MIN_VALUE)));
            }
            criteria.setNodeId(null);
        }

        return deviceManager.queryList(criteria);
    }

    public void addChildDevice(String parentDeviceId, List<String> childDeviceIds) {
        DeviceCriteria criteria = DeviceCriteria.builder()
                .ids(childDeviceIds)
                .build();
        List<Device> devices = deviceManager.queryList(criteria);
        List<Device> canAddDevices = devices.stream()
                .filter(i -> StringUtils.isBlank(i.getGatewayId()) && (!Objects.equals(parentDeviceId, i.getId())))
                .collect(Collectors.toList());
        for (Device canAddDevice : canAddDevices) {
            canAddDevice.setGatewayId(parentDeviceId);
        }
        deviceManager.updateBatchById(canAddDevices);
    }

    public void removeChildDevice(String parentDeviceId, List<String> childDeviceIds) {
        deviceManager.removeChildDevice(parentDeviceId, childDeviceIds);
    }

    public Optional<Device> queryByName(String name) {
        return deviceManager.queryByName(name);
    }

    @Cacheable(value = {"device"}, key = "'name:'+#name")
    public Device queryByNameCache(String name) {
        return deviceManager.queryByName(name).orElse(null);
    }

    public String startPushStreaming(String deviceId) {
        Device device = deviceManager.queryById(deviceId)
                .orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));

        Map<String, Object> params = Maps.newHashMap();
        String rtspUrl = RTSP_URL.replaceAll("\\{host}", streamingMediaHost)
                .replaceAll("\\{port}", String.valueOf(streamingMediaRtspPort))
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());
        params.put("pushUrl", rtspUrl);
        ServiceInvokeMessage message = ServiceInvokeMessage.builder()
                .productId(device.getProductId())
                .deviceName(device.getName())
                .identifier("StartPushStreaming")
                .timestamp(System.currentTimeMillis())
                .params(params)
                .build();
        deviceThingModelEventPublisher.asyncPublishServiceEvent(message);
        return FLV_URL.replaceAll("\\{host}", streamingMediaHost)
                .replaceAll("\\{port}", String.valueOf(streamingMediaHttpPort))
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());
    }

    public void stopPushStreaming(String deviceId) {
        Device device = deviceManager.queryById(deviceId)
                .orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));

        ServiceInvokeMessage message = ServiceInvokeMessage.builder()
                .productId(device.getProductId())
                .deviceName(device.getName())
                .identifier("StopPushStreaming")
                .timestamp(System.currentTimeMillis())
                .build();
        deviceThingModelEventPublisher.asyncPublishServiceEvent(message);
    }
}