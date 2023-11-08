package ms.phecda.rest.ssp.sdk.device;

import ms.phecda.rest.ssp.sdk.device.rep.CallDeviceServiceRep;
import ms.phecda.rest.ssp.sdk.device.req.CallDeviceServiceReq;
import ms.phecda.rest.ssp.sdk.device.req.QueryDeviceReq;
import ms.phecda.rest.ssp.sdk.device.rep.DeviceRep;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DeviceRest {
    String DEVICE_URI = "device/";

    @PostMapping(DEVICE_URI + "devices/query-by-condition")
    List<DeviceRep> find(@RequestBody QueryDeviceReq req);

    @GetMapping(DEVICE_URI + "devices/name/{name}")
    DeviceRep findByName(@PathVariable(value = "name") String name);

    @GetMapping(value = DEVICE_URI + "devices/{id}/streaming/start")
    String startPushStreaming(@PathVariable(value = "id") String id);

    @GetMapping(value = DEVICE_URI + "devices/{id}/streaming/stop")
    void stopPushStreaming(@PathVariable(value = "id") String id);

    @PostMapping(value = DEVICE_URI + "devices/service/call")
    CallDeviceServiceRep callDeviceService(@RequestBody CallDeviceServiceReq req);
}
