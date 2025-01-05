package com.trionesdev.phecda.foundation.core.domains.alarm.manager.dto;

import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO extends AlarmPO {
    private String typeLabel;
    private String levelLabel;
    private String statusLabel;
}
