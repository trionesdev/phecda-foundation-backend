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
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import ms.triones.backend.core.modules.device.support.DeviceConvertMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceManager deviceManager;
    private final ProductManager productManager;
    private final EdgeDeviceClient edgeDeviceClient;

    public void createDevice(Device device) {
        deviceManager.create(device);
    }

    public void deleteDeviceById(String id) {
        deviceManager.deleteById(id);
    }

    public void updateById(Device device) {
        deviceManager.updateById(device);
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
            addDeviceRequest.setNodeId(gatewayDevice.getGatewayKey());
        }
        edgeDeviceClient.addDevice(addDeviceRequest);
        //endregion
        device.setEnabled(true);
        deviceManager.updateById(device);
    }

    /**
     * 设备下线
     * @param deviceId
     */
    public void deviceOffline(String deviceId) {
        Device device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        RemoveDeviceRequest removeDeviceRequest = RemoveDeviceRequest.builder().deviceName(device.getName()).build();
        edgeDeviceClient.removeDevice(removeDeviceRequest);
        device.setEnabled(false);
        deviceManager.updateById(device);
    }

}
