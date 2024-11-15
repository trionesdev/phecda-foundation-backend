package com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionTrigger {
    private TriggerMode triggerMode;
    private Integer duration; //持续事时间
    private Long interval; //间隔时间

    @Getter
    @AllArgsConstructor
    public enum TriggerMode {
        CONTINUOUS("持续触发"),
        SINGLE("单次触发");

        private final String label;

    }
}
