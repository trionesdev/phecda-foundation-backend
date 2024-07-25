package ms.phecda.backend.core.domains.device.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent.Type;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DeviceEventLogCriteria {
    private String deviceName;
    private String eventIdentifier;
    private Type eventType;
    private Instant startTime;
    private Instant endTime;

    private Integer pageNum;
    private Integer pageSize;
}
