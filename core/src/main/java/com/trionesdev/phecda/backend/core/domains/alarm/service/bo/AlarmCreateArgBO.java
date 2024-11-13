package com.trionesdev.phecda.backend.core.domains.alarm.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.core.domains.alarm.dao.entity.Alarm;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmCreateArgBO {
    private String type;
    private String level;
    private String productKey;
    private String deviceName;
    private String description;
    private List<Alarm.Item> eventData;
}
