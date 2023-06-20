package ms.phecda.edgex.device;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import ms.phecda.edge.device.EdgeDeviceClient;
import ms.phecda.edge.device.req.AddDeviceRequest;
import ms.phecda.edge.device.req.DeviceCmdRequest;
import ms.phecda.edge.device.req.RemoveDeviceRequest;
import ms.phecda.edge.device.req.UpdateDeviceRequest;
import ms.phecda.edge.ex.EdgeException;
import ms.phecda.edgex.device.req.ManageDeviceRequest;
import ms.phecda.edgex.device.req.SendDeviceCmdRequest;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EdgexDeviceClient implements EdgeDeviceClient {
    private final IMqttAsyncClient mqttClient;
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
        ManageDeviceRequest mdReq = ManageDeviceRequest.builder()
                .nodeId(request.getNodeId())
                .action(ManageDeviceRequest.Action.ADD)
                .add(ManageDeviceRequest.Add.builder().deviceName(request.getDeviceName()).build())
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
            mqttClient.publish(StrUtil.join("/", request.getNodeId(), DEVICE_CMD_TOPIC), mqttMessage);
        } catch (MqttException ex) {
            throw new EdgeException(ex);
        }
    }

    public void manageDevice(ManageDeviceRequest request) {
        MqttMessage mqttMessage = new MqttMessage();
        try {
            mqttClient.publish(StrUtil.join("/", request.getNodeId(), DEVICE_MANAGEMENT_TOPIC), mqttMessage);
        } catch (MqttException ex) {
            throw new EdgeException(ex);
        }
    }
}
