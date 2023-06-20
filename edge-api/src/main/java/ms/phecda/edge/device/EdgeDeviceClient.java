package ms.phecda.edge.device;

import ms.phecda.edge.device.req.*;

public interface EdgeDeviceClient {
    void execSetCmd(DeviceCmdRequest request);

    void addDevice(AddDeviceRequest request);

    void removeDevice(RemoveDeviceRequest request);

    void updateDevice(UpdateDeviceRequest request);

}
