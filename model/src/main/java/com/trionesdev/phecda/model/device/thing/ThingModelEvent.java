package com.trionesdev.phecda.model.device.thing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingModelEvent extends ThingModelAbility {
    private Type type;
    private List<ValueItem> outputData;

    @AllArgsConstructor
    @Getter
    public enum Type {
        INFO("Info"),
        WARN("Warn"),
        ERROR("Error");

        private final String value;
    }
}
