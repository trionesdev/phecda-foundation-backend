package ms.phecda.edgex.device;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import ms.phecda.edge.device.DeviceClient;
import ms.phecda.edge.device.req.*;
import ms.phecda.edge.ex.EdgeException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@RequiredArgsConstructor
public class EdgexDeviceClient implements DeviceClient {
    private final IMqttClient mqttClient;

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
            mqttClient.publish(StrUtil.join("/", request.getNodeId(), "device/cmd/exec"), mqttMessage);
        } catch (MqttException ex) {
            throw new EdgeException(ex);
        }
    }

    public void manageDevice(ManageDeviceRequest request) {
        MqttMessage mqttMessage = new MqttMessage();
        try {
            mqttClient.publish(StrUtil.join("/", request.getNodeId(), "device/management"), mqttMessage);
        } catch (MqttException ex) {
            throw new EdgeException(ex);
        }
    }
}
