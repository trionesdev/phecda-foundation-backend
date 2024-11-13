package com.trionesdev.phecda.backend.core.domains.linkage.internal.rule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.statecondition.ConditionOperatorEnum;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.statecondition.StateCondition;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.trigger.EventTrigger;

import java.util.List;

@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Scene {

    private EventTrigger eventTrigger;
    private List<OperatorCondition> conditions;


    @Data
    @Accessors(chain = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OperatorCondition {
        private ConditionOperatorEnum operator;
        private List<StateCondition> children;
    }
}
