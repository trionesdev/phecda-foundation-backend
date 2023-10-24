package ms.phecda.rest.ssp.sdk.device;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "${dubhe.phecda.ssp.name:dubhe-phecda}",
        url = "${dubhe.phecda.ssp.url:}",
        path = "${dubhe.phecda.ssp.path:ssp-api/phecda}"
)
public interface DeviceFeignClient extends DeviceRest {
}
