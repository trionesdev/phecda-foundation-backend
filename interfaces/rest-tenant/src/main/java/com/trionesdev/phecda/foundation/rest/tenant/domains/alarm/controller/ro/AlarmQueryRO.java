package com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro;

import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmPO;
import lombok.Data;

import java.time.Instant;

@Data
public class AlarmQueryRO {
    private String type;
    private String level;
    private String productKey;
    private String deviceName;
    private Instant startTime;
    private Instant endTime;
    private AlarmPO.Status status;
    private Integer limit;
}
