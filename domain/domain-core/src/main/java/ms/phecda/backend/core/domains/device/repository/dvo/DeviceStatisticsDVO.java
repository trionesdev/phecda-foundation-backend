package ms.phecda.backend.core.domains.device.repository.dvo;

import lombok.Data;

@Data
public class DeviceStatisticsDVO {
    private Integer count;
    private Integer enabledCount;
    private Integer disabledCount;
}
