package com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.query;

import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.Alarm;

import java.time.Instant;

@Data
public class AlarmQuery {
    private String type;
    private String level;
    private String productKey;
    private String deviceName;
    private Instant startTime;
    private Instant endTime;
    private Alarm.Status status;
    private Integer limit;
}
