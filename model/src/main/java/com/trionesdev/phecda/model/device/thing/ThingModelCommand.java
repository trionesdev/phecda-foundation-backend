package com.trionesdev.phecda.model.device.thing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 物模型指令
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingModelCommand extends ThingModelAbility {
    private CallType callType;
    private Boolean required;
    private List<ValueItem> inputData;
    private List<ValueItem> outputData;

    @AllArgsConstructor
    @Getter
    public enum CallType {
        ASYNC("Async"),
        SYNC("Sync");

        private final String value;
    }
}
