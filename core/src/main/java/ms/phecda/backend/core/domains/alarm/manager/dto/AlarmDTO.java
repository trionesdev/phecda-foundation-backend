package ms.phecda.backend.core.domains.alarm.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.alarm.dao.entity.Alarm;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO extends Alarm {
    private String typeLabel;
    private String levelLabel;
    private String statusLabel;
}
