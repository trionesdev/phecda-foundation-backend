package com.trionesdev.phecda.model.device.thing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 物模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingModel {
    private List<ThingModelEvent> events = new ArrayList<>();
    private List<ThingModelProperty> properties = new ArrayList<>();
    private List<ThingModelCommand> commands = new ArrayList<>();
}
