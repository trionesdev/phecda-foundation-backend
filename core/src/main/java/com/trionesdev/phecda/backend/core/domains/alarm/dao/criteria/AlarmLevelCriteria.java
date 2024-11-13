package com.trionesdev.phecda.backend.core.domains.alarm.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmLevelCriteria {
    private Boolean enabled;
}
