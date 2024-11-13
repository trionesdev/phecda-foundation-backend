package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 物模型能力
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class ThingModelAbility {
    private String identifier;
    private String name;
    private String description;
}
