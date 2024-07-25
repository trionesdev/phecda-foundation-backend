package ms.phecda.backend.core.domains.device.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStatisticsMessageDailyCriteria {
    private Instant startTime;
    private Instant endTime;
    private String type;
    private LocalDate date;
}
