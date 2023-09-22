package ms.phecda.backend.core.messageaccess.event.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.moensun.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.messageaccess.event.DeviceDisableEvent;
import ms.phecda.backend.core.messageaccess.event.DeviceEnableEvent;
import ms.phecda.backend.core.modules.device.dao.entity.Device;
import ms.phecda.backend.core.modules.device.dao.entity.Product;
import ms.phecda.backend.core.modules.device.service.impl.ProductService;
import ms.phecda.backend.core.modules.edge.dao.entity.Node;
import ms.phecda.backend.core.modules.edge.dao.entity.NodeDevice;
import ms.phecda.backend.core.modules.edge.service.impl.NodeDeviceService;
import ms.phecda.backend.core.modules.edge.service.impl.NodeService;
import ms.phecda.edge.base.device.EdgeDeviceClient;
import ms.phecda.edge.base.device.req.AddDeviceRequest;
import ms.phecda.edge.base.device.req.RemoveDeviceRequest;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class DeviceManageHandler {
    private final EdgeDeviceClient edgeDeviceClient;
    private final ProductService productService;
    private final NodeService nodeService;
    private final NodeDeviceService nodeDeviceService;

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    public void trySendRemoveDeviceCmd(DeviceDisableEvent<Device> event) {
        Device device = (Device) event.getSource();
        if (Objects.isNull(device)) {
            return;
        }

        RemoveDeviceRequest removeDeviceRequest = RemoveDeviceRequest.builder().deviceName(device.getName()).build();

        NodeDevice nodeDevice = nodeDeviceService.getByDeviceId(device.getId());
        if (Objects.isNull(nodeDevice)) {
            return;
        }

        Node node = nodeService.getById(nodeDevice.getNodeId());
        if (Objects.isNull(node)) {
            throw new NotFoundException("NODE_NOT_FOUND");
        }
        removeDeviceRequest.setNodeId(node.getIdentifier());

        edgeDeviceClient.removeDevice(removeDeviceRequest);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    public void trySendAddDeviceCmd(DeviceEnableEvent<Device> event) {
        Device device = (Device) event.getSource();
        if (Objects.isNull(device)) {
            return;
        }

        Product product = productService.queryProductById(device.getProductId())
                .orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));

        NodeDevice nodeDevice = nodeDeviceService.getByDeviceId(device.getId());
        if (Objects.isNull(nodeDevice)) {
            return;
        }

        //region edge add device
        AddDeviceRequest addDeviceRequest = AddDeviceRequest.builder()
                .productId(device.getProductId())
                .deviceName(device.getName())
                .thingModelVersion(product.getThingModelVersion())
                .build();
        addDeviceRequest.setDriver(product.getDriverName());
        Map<String, Object> protocols = Maps.newHashMap();
        if (CollectionUtil.isNotEmpty(device.getProtocols())) {
            device.getProtocols().forEach(t -> protocols.put(t.getName(), t.getValue()));
        }
        addDeviceRequest.setProtocols(protocols);

        Node node = nodeService.getById(nodeDevice.getNodeId());
        if (Objects.isNull(node)) {
            throw new NotFoundException("NODE_NOT_FOUND");
        }
        addDeviceRequest.setNodeId(node.getIdentifier());

        edgeDeviceClient.addDevice(addDeviceRequest);
    }
}
