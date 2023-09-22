package ms.phecda.backend.core.modules.device.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.modules.device.manager.dto.ProductDTO;
import ms.phecda.backend.core.modules.device.dao.entity.Device;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceExtBO extends Device {
    private ProductDTO product;
}
