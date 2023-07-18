package ms.triones.backend.core.modules.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.core.util.PageUtils;
import com.moensun.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import ms.phecda.edge.device.EdgeDeviceClient;
import ms.phecda.edge.device.req.AddDeviceRequest;
import ms.phecda.edge.device.req.RemoveDeviceRequest;
import ms.triones.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.manager.dto.ProductDTO;
import ms.triones.backend.core.modules.device.manager.impl.DeviceManager;
import ms.triones.backend.core.modules.device.manager.impl.ProductManager;
import ms.triones.backend.core.modules.device.manager.impl.ProductThingModelVersionManager;
import ms.triones.backend.core.modules.device.service.bo.DeviceEventDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import ms.triones.backend.core.modules.device.service.bo.DevicePropertyDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceServiceDataBO;
import ms.triones.backend.core.modules.device.support.DeviceConvertMapper;
import ms.triones.backend.core.provider.ssp.asset.impl.AssetProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceManager deviceManager;
    private final ProductManager productManager;
    private final ProductThingModelVersionManager productThingModelVersionManager;
    private final EdgeDeviceClient edgeDeviceClient;
    private final AssetProvider assetProvider;

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
    public void deviceOnline(String deviceId) {
        Device device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        Product product = productManager.queryById(device.getProductId()).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        //region edge add device
        AddDeviceRequest addDeviceRequest = AddDeviceRequest.builder().deviceName(device.getName()).build();
        addDeviceRequest.setDriver(product.getDriverName());
        Map<String, Object> protocols = Maps.newHashMap();
        if (CollectionUtil.isNotEmpty(device.getProtocols())) {
            device.getProtocols().forEach(t -> protocols.put(t.getName(), t.getValue()));
        }
        addDeviceRequest.setProtocols(protocols);
        if (StrUtil.isNotBlank(device.getGatewayDeviceId())) {
            Device gatewayDevice = deviceManager.queryById(device.getGatewayDeviceId()).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
            addDeviceRequest.setNodeId(gatewayDevice.getGatewayIdentifier());
        }
        edgeDeviceClient.addDevice(addDeviceRequest);
        //endregion
        deviceManager.updateById(Device.builder().id(deviceId).enabled(true).build());
    }

    /**
     * 设备下线
     *
     * @param deviceId
     */
    public void deviceOffline(String deviceId) {
        Device device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        RemoveDeviceRequest removeDeviceRequest = RemoveDeviceRequest.builder().deviceName(device.getName()).build();
        edgeDeviceClient.removeDevice(removeDeviceRequest);
        deviceManager.updateById(Device.builder().id(deviceId).enabled(false).build());
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
        Set<String> deviceNameSet = assetProvider.queryRelationDeviceNames(assetSn);
        List<Device> devices = deviceManager.listAll();
        if (CollectionUtils.isEmpty(deviceNameSet)) {
            return devices;
        }

        return devices.stream().filter(device -> !deviceNameSet.contains(device.getName())).collect(Collectors.toList());
    }

    public List<Device> queryAssetRelationDevice(String assetSn) {
        List<String> deviceNames = assetProvider.queryAssetRelationDeviceNames(assetSn);
        if (CollectionUtils.isEmpty(deviceNames)) {
            return Collections.emptyList();
        }

        DeviceCriteria criteria = DeviceCriteria.builder().names(deviceNames).build();
        return deviceManager.queryList(criteria);
    }

    public List<DevicePropertyDataBO> queryDeviceProperties(String deviceName) {
        Optional<Device> deviceOptional = deviceManager.queryByName(deviceName);
        Device device = deviceOptional.orElse(Device.builder().build());
        return productManager.findThingModel(device.getProductId()).map(thingModel ->
                thingModel.getProperties().stream().map(property -> DeviceConvertMapper.INSTANCE.from(property))
                        .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public List<Device> queryList(DeviceCriteria criteria) {
        return deviceManager.queryList(criteria);
    }
}
