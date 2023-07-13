package ms.triones.backend.core.modules.device.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCriteria {
    private String productId;

    private Collection<String> names;
}
