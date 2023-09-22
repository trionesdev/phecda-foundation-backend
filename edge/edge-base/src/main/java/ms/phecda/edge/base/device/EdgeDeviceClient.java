package ms.phecda.edge.base.device;

import ms.phecda.edge.base.device.model.AddDeviceRequest;
import ms.phecda.edge.base.device.model.DeviceCmdRequest;
import ms.phecda.edge.base.device.model.RemoveDeviceRequest;
import ms.phecda.edge.base.device.model.UpdateDeviceRequest;

public interface EdgeDeviceClient {
    void execSetCmd(DeviceCmdRequest request);

    void addDevice(AddDeviceRequest request);

    void removeDevice(RemoveDeviceRequest request);

    void updateDevice(UpdateDeviceRequest request);

}
