package ms.triones.backend.rest.backend.modules.device.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductThingModelRO;
import ms.triones.backend.rest.backend.modules.device.support.DeviceConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "产品")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class ProductController {

    @Operation(summary = "新增物模型功能")
    @PutMapping(value = "products/{productId}/thing-model")
    public void upsertThingModel(
            @PathVariable(value = "productId") String productId,
            @Validated @RequestBody ProductThingModelRO args
    ) {

    }

}
