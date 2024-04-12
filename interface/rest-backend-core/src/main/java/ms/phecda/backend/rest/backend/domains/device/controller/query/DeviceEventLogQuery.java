package ms.phecda.backend.rest.backend.domains.device.controller.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelEvent.Type;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DeviceEventLogQuery {
    private String deviceName;
    private String eventIdentifier;
    private Type eventType;
    private Instant startTime;
    private Instant endTime;

    private Integer pageNum;
    private Integer pageSize;
}
