package ms.phecda.backend.core.domains.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.JsonUtils;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.dvo.DeviceStatisticsDVO;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceServiceLog.Result;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.enums.AccessChannelEnum;
import ms.phecda.backend.core.domains.device.manager.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.manager.dto.ServiceSendDTO;
import ms.phecda.backend.core.domains.device.manager.impl.DeviceDataManager;
import ms.phecda.backend.core.domains.device.manager.impl.DeviceManager;
import ms.phecda.backend.core.domains.device.manager.impl.ProductManager;
import ms.phecda.backend.core.domains.device.service.bo.*;
import ms.phecda.backend.core.domains.device.internal.DeviceConvertMapper;
import ms.phecda.backend.core.domains.device.internal.thing.model.ThingModel;
import ms.phecda.backend.core.domains.device.internal.thing.model.ThingModelService;
import ms.phecda.backend.core.domains.device.internal.thing.model.ThingModelService.CallType;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeReplyMessage;
import ms.phecda.backend.core.provider.ssp.edge.impl.NodeDeviceProvider;
import ms.phecda.backend.core.provider.ssp.edge.pdo.NodeDevicePDO;
import ms.phecda.backend.core.provider.ssp.gatweay.impl.GatewayProvider;
import ms.phecda.backend.core.provider.ssp.gatweay.pdo.CommandSendPDO;
import ms.phecda.backend.core.internal.util.TopicUtils;
import ms.phecda.infrastructure.conf.mqtt.PhecdaMqtt;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceManager deviceManager;
    private final ProductManager productManager;
    private final DeviceDataManager deviceDataManager;
    private final NodeDeviceProvider nodeDeviceProvider;
    private final PhecdaMqtt phecdaMqtt;
    private final GatewayProvider gatewayProvider;

    @Value("${streaming.media.host}")
    private String streamingMediaHost = "127.0.0.1";
    @Value("${streaming.media.rtmp.port}")
    private Integer streamingMediaRtmpPort = 1935;
    @Value("${streaming.media.http.port}")
    private Integer streamingMediaHttpPort = 80;

    private static final String RTMP_URL = "rtmp://{host}:{port}/{productId}/{deviceName}";
    private static final String FLV_URL = "http://{host}:{port}/{productId}/{deviceName}.live.flv";

    public DeviceStatisticsDVO statistics() {
        return deviceManager.queryStatusStatistics();
    }


    public void createDevice(Device device) {
        deviceManager.create(device);
    }

    public void deleteDeviceById(String id) {
        deviceManager.queryById(id).ifPresent(t -> {
            deviceManager.deleteById(t);
            deviceManager.cleanDeviceCache(t);
        });
    }

    public void updateById(Device device) {
        Objects.requireNonNull(device.getId());
        deviceManager.updateById(device);
        deviceManager.cleanDeviceCache(device);
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
        deviceManager.cleanDeviceCache(device);
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
        deviceManager.cleanDeviceCache(device);
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

    @Cacheable(value = {"devices"}, key = "'name:'+#name")
    public Device queryByNameCache(String name) {
        return deviceManager.queryByName(name).orElse(null);
    }

    public String startPushStreamingByName(String name) {
        Optional<Device> deviceOptional = deviceManager.queryByName(name);
        return deviceOptional.map(this::startPushStreaming).orElse(null);
    }

    public void stopPushStreamingByName(String name) {
        Optional<Device> deviceOptional = deviceManager.queryByName(name);
        deviceOptional.ifPresent(this::stopPushStreaming);
    }

    public String startPushStreaming(String id) {
        Optional<Device> deviceOptional = deviceManager.queryById(id);
        return deviceOptional.map(this::startPushStreaming).orElse(null);
    }

    public void stopPushStreaming(String id) {
        Optional<Device> deviceOptional = deviceManager.queryById(id);
        deviceOptional.ifPresent(this::stopPushStreaming);
    }

    public String startPushStreaming(Device device) {
        String rtmpUrl = RTMP_URL.replaceAll("\\{host}", streamingMediaHost)
                .replaceAll("\\{port}", String.valueOf(streamingMediaRtmpPort))
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());

        Map<String, String> params = Maps.newHashMap();
        params.put("pushUrl", rtmpUrl);
        InvokeServiceArgBO args = InvokeServiceArgBO.builder()
                .identifier("StartPushStreaming")
                .params(params)
                .build();
        invokeService(device.getId(), args);
        return FLV_URL.replaceAll("\\{host}", streamingMediaHost)
                .replaceAll("\\{port}", String.valueOf(streamingMediaHttpPort))
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());
    }

    public void stopPushStreaming(Device device) {
        InvokeServiceArgBO args = InvokeServiceArgBO.builder()
                .identifier("StopPushStreaming")
                .build();
        invokeService(device.getId(), args);
    }

    public ServiceInvokeReplyMessage sendServiceWithDeviceName(String deviceName, InvokeServiceArgBO args) {
        Device device = deviceManager.queryByName(deviceName).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return invokeService(device.getId(), args);
    }

    /**
     * 调用服务
     *
     * @param id
     * @param args
     * @return
     */
    public ServiceInvokeReplyMessage invokeService(String id, InvokeServiceArgBO args) {
        Device device = deviceManager.queryById(id).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        Product product = productManager.queryById(device.getProductId()).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        ThingModel thingModel = productManager.findThingModel(product.getId()).orElseThrow(() -> new NotFoundException("THING_MODEL_NOT_FOUND"));
        ThingModelService service = thingModel.getServices().stream()
                .filter(i -> i.getIdentifier().equals(args.getIdentifier()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("SERVICE_NOT_FOUND"));
        ServiceSendDTO dto = ServiceSendDTO.builder()
                .id(UUID.randomUUID().toString())
                .sync(service.getCallType().equals(CallType.SYNC))
//                .method(service.getIdentifier())
                .productKey(product.getKey())
                .deviceName(device.getName())
                .params(args.getParams())
                .commandName(args.getIdentifier())
                .body(args.getBody())
                .build();
        return invokeService(product, service.getCallType(), dto, args.getTags());
    }


    public ServiceInvokeReplyMessage invokeService(String productKey, String deviceName, InvokeServiceArgBO args) {
        Product product = productManager.findByKey(productKey).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        ThingModel thingModel = productManager.findThingModel(product.getId()).orElseThrow(() -> new NotFoundException("THING_MODEL_NOT_FOUND"));
        ThingModelService service = thingModel.getServices().stream()
                .filter(i -> i.getIdentifier().equals(args.getIdentifier()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("SERVICE_NOT_FOUND"));
        ServiceSendDTO dto = ServiceSendDTO.builder()
                .id(UUID.randomUUID().toString())
                .sync(service.getCallType().equals(CallType.SYNC))
                .productKey(productKey)
                .deviceName(deviceName)
                .params(args.getParams())
                .commandName(args.getIdentifier())
                .body(args.getBody())
                .build();
        return invokeService(product, service.getCallType(), dto, args.getTags());
    }


    public ServiceInvokeReplyMessage invokeService(Product product, CallType callType, ServiceSendDTO dto, Map<String, String> tags) {
        AccessChannelEnum channel = product.getAccessChannel();

        DeviceServiceLog serviceLog = DeviceServiceLog.builder()
                .messageId(dto.getId())
                .productId(product.getId())
                .deviceName(dto.getDeviceName())
                .productId(product.getId())
                .serviceIdentifier(dto.getCommandName())
                .serviceName(dto.getCommandName())
                .sync(dto.getSync())
                .inputData(JsonUtils.toJsonString(dto.getBody()))
                .tags(tags)
                .build();

        try {
            switch (channel) {
                case MQTT:
                    String sendTopic = TopicUtils.serviceSendTopic(dto.getProductKey(), dto.getDeviceName(), dto.getCommandName());
                    if (CallType.ASYNC.equals(callType)) {
                        phecdaMqtt.publish(sendTopic, JSON.toJSONBytes(dto));
                        serviceLog.setResult(Result.SUCCESS);
                    } else if (CallType.SYNC.equals(callType)) {
                        String replayTopic = TopicUtils.serviceSyncReplyTopic(dto.getId());
                        MqttMessage replayMessage = phecdaMqtt.publishAsync(sendTopic, replayTopic, dto.getId(), JSON.toJSONBytes(dto));
                        serviceLog.setResult(Result.SUCCESS).setInputData(Optional.ofNullable(replayMessage.getPayload()).map(String::new).orElse(null));
                        return JSON.parseObject(replayMessage.getPayload(), ServiceInvokeReplyMessage.class);
                    }
                    break;
                case GATEWAY:
                    CommandSendPDO command = CommandSendPDO.builder()
                            .id(dto.getId())
                            .method(dto.getMethod())
                            .productKey(dto.getProductKey())
                            .deviceName(dto.getDeviceName())
                            .commandName(dto.getCommandName())
                            .params(dto.getParams())
                            .body(dto.getBody())
                            .version(dto.getVersion())
                            .build();
                    if (CallType.ASYNC.equals(callType)) {
                        CompletableFuture.supplyAsync(() -> gatewayProvider.sendCommand(command));
                        serviceLog.setResult(Result.SUCCESS);
                    } else if (CallType.SYNC.equals(callType)) {
//                    return gatewayProvider.sendCommand(command);
                        return null;
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            serviceLog.setResult(Result.FAILURE).setErrorMessage(e.getMessage());
        } finally {
            deviceDataManager.saveServiceLog(serviceLog);
        }
        return null;
    }

}