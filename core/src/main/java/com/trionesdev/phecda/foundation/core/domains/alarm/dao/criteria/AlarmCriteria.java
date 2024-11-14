package com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria;

import com.trionesdev.commons.core.page.PageCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.Alarm;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmCriteria extends PageCriteria {
    private String type;
    private String level;
    private String productKey;
    private String deviceName;
    private Instant startTime;
    private Instant endTime;
    private Alarm.Status status;
    private Integer limit;
}
