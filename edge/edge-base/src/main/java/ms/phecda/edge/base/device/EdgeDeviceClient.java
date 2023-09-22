package ms.phecda.edge.base.device;

import ms.phecda.edge.base.device.req.AddDeviceRequest;
import ms.phecda.edge.base.device.req.DeviceCmdRequest;
import ms.phecda.edge.base.device.req.RemoveDeviceRequest;
import ms.phecda.edge.base.device.req.UpdateDeviceRequest;

public interface EdgeDeviceClient {
    void execSetCmd(DeviceCmdRequest request);

    void addDevice(AddDeviceRequest request);

    void removeDevice(RemoveDeviceRequest request);

    void updateDevice(UpdateDeviceRequest request);

}
