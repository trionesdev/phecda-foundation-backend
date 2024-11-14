package com.trionesdev.phecda.foundation.core.domains.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.JsonUtils;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.commons.exception.NotFoundException;
import com.trionesdev.phecda.foundation.core.domains.device.dto.*;
import com.trionesdev.phecda.foundation.core.domains.device.service.bo.*;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.dvo.DeviceStatisticsDVO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceServiceLogPO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceServiceLogPO.Result;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductPO;
import com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.foundation.core.domains.device.internal.enums.AccessChannel;
import com.trionesdev.phecda.foundation.core.domains.device.manager.impl.DeviceDataManager;
import com.trionesdev.phecda.foundation.core.domains.device.manager.impl.DeviceManager;
import com.trionesdev.phecda.foundation.core.domains.device.manager.impl.ProductManager;
import com.trionesdev.phecda.model.device.thing.ThingModel;
import com.trionesdev.phecda.model.device.thing.ThingModelCommand;
import com.trionesdev.phecda.model.device.thing.ThingModelCommand.CallType;
import com.trionesdev.phecda.foundation.core.messageaccess.model.ServiceInvokeReplyMessage;
import com.trionesdev.phecda.foundation.core.facade.ssp.edge.impl.NodeDeviceProvider;
import com.trionesdev.phecda.foundation.core.facade.ssp.edge.pdo.NodeDevicePDO;
import com.trionesdev.phecda.foundation.core.facade.ssp.gatweay.impl.GatewayProvider;
import com.trionesdev.phecda.foundation.core.facade.ssp.gatweay.pdo.CommandSendPDO;
import com.trionesdev.phecda.foundation.core.internal.util.TopicUtils;
import com.trionesdev.phecda.infrastructure.configuration.mqtt.PhecdaMqtt;
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


    public void createDevice(DevicePO device) {
        deviceManager.create(device);
    }

    public void deleteDeviceById(String id) {
        deviceManager.queryById(id).ifPresent(t -> {
            deviceManager.deleteById(t);
            deviceManager.cleanDeviceCache(t);
        });
    }

    public void updateById(DevicePO device) {
        Objects.requireNonNull(device.getId());
        deviceManager.updateById(device);
        deviceManager.cleanDeviceCache(device);
    }

    public Optional<DeviceExtDTO> queryExtById(String id) {
        return deviceManager.queryById(id).map(t -> {
            DeviceExtDTO deviceExt = DeviceDomainConvert.INSTANCE.from(t);
            productManager.queryExtById(t.getProductId()).ifPresent(deviceExt::setProduct);
            return deviceExt;
        });
    }

    public PageInfo<DeviceExtDTO> queryExtPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
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

        PageInfo<DevicePO> pageInfo = deviceManager.queryPage(pageNum, pageSize, criteria);
        if (CollectionUtil.isEmpty(pageInfo.getRows())) {
            return PageUtils.empty();
        }
        Set<String> productIds = pageInfo.getRows().stream().map(DevicePO::getProductId).collect(Collectors.toSet());
        Map<String, ProductExtDTO> productMap = productManager.queryAllByIds(productIds).stream().collect(Collectors.toMap(ProductPO::getId, v -> v, (v1, v2) -> v1));
        List<DeviceExtDTO> deviceExtList = pageInfo.getRows().stream().map(t -> {
            DeviceExtDTO deviceExt = DeviceDomainConvert.INSTANCE.from(t);
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
        DevicePO device = deviceManager.queryById(deviceId)
                .orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));

        deviceManager.updateById(DevicePO.builder()
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
        DevicePO device = deviceManager.queryById(deviceId)
                .orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));

        deviceManager.updateById(DevicePO.builder()
                .id(deviceId)
                .enabled(false)
                .build());
        deviceManager.cleanDeviceCache(device);
    }

    public List<DevicePropertyDataBO> queryDeviceThingModelPropertiesData(String deviceId) {
        DevicePO device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productManager.findThingModel(device.getProductId()).map(thingModel -> {
            return thingModel.getProperties().stream().map(property -> {
                DevicePropertyDataBO devicePropertyData = DeviceDomainConvert.INSTANCE.from(property);
                return devicePropertyData;
            }).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }

    public List<DeviceEventDataBO> queryDeviceThingModelEventsData(String deviceId) {
        DevicePO device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productManager.findThingModel(device.getProductId()).map(thingModel -> {
            return thingModel.getEvents().stream().map(property -> {
                DeviceEventDataBO deviceEventData = DeviceDomainConvert.INSTANCE.from(property);
                return deviceEventData;
            }).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }

    public List<DeviceServiceDataBO> queryDeviceThingModelServicesData(String deviceId) {
        DevicePO device = deviceManager.queryById(deviceId).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return productManager.findThingModel(device.getProductId()).map(thingModel -> {
            return thingModel.getCommands().stream().map(property -> {
                DeviceServiceDataBO deviceServiceData = DeviceDomainConvert.INSTANCE.from(property);
                return deviceServiceData;
            }).collect(Collectors.toList());
        }).orElse(Collections.emptyList());
    }

    public List<DevicePO> queryAllDevice() {
        return deviceManager.listAll();
    }

    public List<DevicePO> queryNoRelationDevice(String assetSn) {
        return Collections.emptyList();
    }

    public List<DevicePO> queryAssetRelationDevice(String assetSn) {
        return Collections.emptyList();
    }

    public List<DevicePropertyDataBO> queryDeviceProperties(String deviceName) {
        Optional<DevicePO> deviceOptional = deviceManager.queryByName(deviceName);
        DevicePO device = deviceOptional.orElse(DevicePO.builder().build());
        return productManager.findThingModel(device.getProductId()).map(thingModel ->
                thingModel.getProperties().stream().map(property -> DeviceDomainConvert.INSTANCE.from(property))
                        .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public List<DevicePO> queryList(DeviceCriteria criteria) {
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
        List<DevicePO> devices = deviceManager.queryList(criteria);
        List<DevicePO> canAddDevices = devices.stream()
                .filter(i -> StringUtils.isBlank(i.getGatewayId()) && (!Objects.equals(parentDeviceId, i.getId())))
                .collect(Collectors.toList());
        for (DevicePO canAddDevice : canAddDevices) {
            canAddDevice.setGatewayId(parentDeviceId);
        }
        deviceManager.updateBatchById(canAddDevices);
    }

    public void removeChildDevice(String parentDeviceId, List<String> childDeviceIds) {
        deviceManager.removeChildDevice(parentDeviceId, childDeviceIds);
    }

    public Optional<DevicePO> queryByName(String name) {
        return deviceManager.queryByName(name);
    }

    @Cacheable(value = {"devices"}, key = "'name:'+#name")
    public DevicePO queryByNameCache(String name) {
        return deviceManager.queryByName(name).orElse(null);
    }

    public String startPushStreamingByName(String name) {
        Optional<DevicePO> deviceOptional = deviceManager.queryByName(name);
        return deviceOptional.map(this::startPushStreaming).orElse(null);
    }

    public void stopPushStreamingByName(String name) {
        Optional<DevicePO> deviceOptional = deviceManager.queryByName(name);
        deviceOptional.ifPresent(this::stopPushStreaming);
    }

    public String startPushStreaming(String id) {
        Optional<DevicePO> deviceOptional = deviceManager.queryById(id);
        return deviceOptional.map(this::startPushStreaming).orElse(null);
    }

    public void stopPushStreaming(String id) {
        Optional<DevicePO> deviceOptional = deviceManager.queryById(id);
        deviceOptional.ifPresent(this::stopPushStreaming);
    }

    public String startPushStreaming(DevicePO device) {
        String rtmpUrl = RTMP_URL.replaceAll("\\{host}", streamingMediaHost)
                .replaceAll("\\{port}", String.valueOf(streamingMediaRtmpPort))
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());

        Map<String, String> params = Maps.newHashMap();
        params.put("pushUrl", rtmpUrl);
        InvokeServiceCmd args = InvokeServiceCmd.builder()
                .identifier("StartPushStreaming")
                .params(params)
                .build();
        invokeService(device.getId(), args);
        return FLV_URL.replaceAll("\\{host}", streamingMediaHost)
                .replaceAll("\\{port}", String.valueOf(streamingMediaHttpPort))
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());
    }

    public void stopPushStreaming(DevicePO device) {
        InvokeServiceCmd args = InvokeServiceCmd.builder()
                .identifier("StopPushStreaming")
                .build();
        invokeService(device.getId(), args);
    }

    public ServiceInvokeReplyMessage sendServiceWithDeviceName(String deviceName, InvokeServiceCmd args) {
        DevicePO device = deviceManager.queryByName(deviceName).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        return invokeService(device.getId(), args);
    }

    /**
     * 调用服务
     *
     * @param id
     * @param args
     * @return
     */
    public ServiceInvokeReplyMessage invokeService(String id, InvokeServiceCmd args) {
        DevicePO device = deviceManager.queryById(id).orElseThrow(() -> new NotFoundException("DEVICE_NOT_FOUND"));
        Product product = productManager.findById(device.getProductId()).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        ThingModel thingModel = productManager.findThingModel(product.getId()).orElseThrow(() -> new NotFoundException("THING_MODEL_NOT_FOUND"));
        ThingModelCommand service = thingModel.getCommands().stream()
                .filter(i -> i.getIdentifier().equals(args.getIdentifier()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("SERVICE_NOT_FOUND"));
        ServiceSendCmd dto = ServiceSendCmd.builder()
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


    public ServiceInvokeReplyMessage invokeService(String productKey, String deviceName, InvokeServiceCmd args) {
        Product product = productManager.findByKey(productKey).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        ThingModel thingModel = productManager.findThingModel(product.getId()).orElseThrow(() -> new NotFoundException("THING_MODEL_NOT_FOUND"));
        ThingModelCommand service = thingModel.getCommands().stream()
                .filter(i -> i.getIdentifier().equals(args.getIdentifier()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("SERVICE_NOT_FOUND"));
        ServiceSendCmd dto = ServiceSendCmd.builder()
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


    public ServiceInvokeReplyMessage invokeService(Product product, CallType callType, ServiceSendCmd dto, Map<String, String> tags) {
        AccessChannel channel = product.getAccessChannel();

        DeviceServiceLogPO serviceLog = DeviceServiceLogPO.builder()
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
                case DRIVER:
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