package com.trionesdev.phecda.backend.core.domains.alarm.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmStatisticsBO {
    private Long total;
    private Long monthTotal;
    private Long dayTotal;
}
