package ms.triones.backend.core.modules.devicedata.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class DeviceDataCriteria {
    private Instant startTime;
    private Instant endTime;
    private String assetSn;
    private String deviceName;
    private String field;
    private String nodeId;
}
