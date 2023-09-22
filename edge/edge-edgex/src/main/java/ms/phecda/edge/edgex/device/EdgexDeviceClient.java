package ms.phecda.edge.edgex.device;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.edge.base.device.EdgeDeviceClient;
import ms.phecda.edge.base.device.model.AddDeviceRequest;
import ms.phecda.edge.base.device.model.DeviceCmdRequest;
import ms.phecda.edge.base.device.model.RemoveDeviceRequest;
import ms.phecda.edge.base.device.model.UpdateDeviceRequest;
import ms.phecda.edge.base.ex.EdgeException;
import ms.phecda.edgex.device.req.ManageDeviceRequest;
import ms.phecda.edgex.device.req.SendDeviceCmdRequest;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EdgexDeviceClient implements EdgeDeviceClient {
    private final IMqttAsyncClient mqttClient;
    private final ObjectMapper objectMapper;
    private final String TOPIC_PREFIX = "/phecda";
    private final String DEVICE_CMD_TOPIC = "device/cmd/exec";
    private final String DEVICE_MANAGEMENT_TOPIC = "device/management";

    @Override
    public void execSetCmd(DeviceCmdRequest request) {
        SendDeviceCmdRequest sdcReq = SendDeviceCmdRequest.builder()
                .nodeId(request.getNodeId())
                .action(SendDeviceCmdRequest.Action.SET)
                .deviceName(request.getDeviceName())
                .commandName(request.getCommandName())
                .commandValue(request.getCommandValue())
                .build();
        sendCmd(sdcReq);
    }

    @Override
    public void addDevice(AddDeviceRequest request) {
        ManageDeviceRequest.Add add = ManageDeviceRequest.Add.builder()
                .driver(request.getDriver())
                .productId(request.getProductId())
                .deviceName(request.getDeviceName())
                .thingModelVersion(request.getThingModelVersion())
                .protocols(request.getProtocols())
                .build();
        ManageDeviceRequest mdReq = ManageDeviceRequest.builder()
                .nodeId(request.getNodeId())
                .action(ManageDeviceRequest.Action.ADD)
                .add(add)
                .build();
        manageDevice(mdReq);
    }

    @Override
    public void removeDevice(RemoveDeviceRequest request) {
        ManageDeviceRequest mdReq = ManageDeviceRequest.builder()
                .nodeId(request.getNodeId())
                .action(ManageDeviceRequest.Action.REMOVE)
                .remove(ManageDeviceRequest.Remove.builder().deviceName(request.getDeviceName()).build())
                .build();
        manageDevice(mdReq);
    }

    @Override
    public void updateDevice(UpdateDeviceRequest request) {

    }

    public void sendCmd(SendDeviceCmdRequest request) {
        MqttMessage mqttMessage = new MqttMessage();
        try {
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(request));
            String topic = "/" + StrUtil.join("/", TOPIC_PREFIX, request.getNodeId(), DEVICE_CMD_TOPIC);
            log.info("send device cmd,topic: {}", topic);
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException | JsonProcessingException ex) {
            log.error(ex.getMessage(), ex);
            throw new EdgeException(ex);
        }
    }

    public void manageDevice(ManageDeviceRequest request) {
        MqttMessage mqttMessage = new MqttMessage();
        try {
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(request));
            String topic = StrUtil.join("/", TOPIC_PREFIX, request.getNodeId(), DEVICE_MANAGEMENT_TOPIC);
            log.info("send device management,topic: {}", topic);
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException | JsonProcessingException ex) {
            log.error(ex.getMessage(), ex);
            throw new EdgeException(ex);
        }
    }
}
