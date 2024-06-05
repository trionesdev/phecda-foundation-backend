package ms.phecda.backend.rest.backend.domains.device.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.rest.backend.domains.device.controller.ro.ProductDriverCreateRO;
import ms.phecda.backend.rest.backend.domains.device.internal.DeviceBeRestConvert;
import ms.phecda.backend.rest.backend.domains.device.internal.DeviceConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "驱动")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class ProductDriverController {
    private final DeviceBeRestConvert convert;

    @Operation(summary = "新建驱动")
    @PostMapping(value = "drivers")
    public void createDriver(@Validated @RequestBody ProductDriverCreateRO args)
    {

    }
}
