package ms.phecda.backend.core.domains.linkage.internal.rule.statecondition;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.linkage.internal.rule.OperatorEnum;
import ms.phecda.backend.core.domains.linkage.internal.util.RuleUtils;
import ms.phecda.backend.core.domains.device.thing.valuetype.ValueTypeEnum;

import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateCondition {

    private StateIdentifier stateIdentifier;
    private Condition condition;
    private List<Condition> conditions;

    @Data
    @Accessors(chain = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXISTING_PROPERTY,
            property = "type",
            visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ThingPropertyValueCondition.StateIdentifier.class, name = "THING_PROPERTY_VALUE")
    })
    @JsonIgnoreProperties(ignoreUnknown = true)
    public abstract static class StateIdentifier {
        private ConditionTypeEnum type;
    }

    @Data
    @Accessors(chain = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Condition {
        private String valuePath;
        private OperatorEnum operator;
        private ValueTypeEnum valueType;
        private List<String> args;
    }

    public String conditionEl() {
        List<String> rules = Lists.newArrayList();

        if (Objects.nonNull(getCondition()) && CollectionUtil.isNotEmpty(getCondition().getArgs())) {
            Condition c = getCondition();
            String express = RuleUtils.expression(c.getValuePath(), c.getOperator(), c.getValueType(), c.getArgs());
            if (StrUtil.isNotBlank(express)) {
                rules.add(express);
            }
        }
        if (CollectionUtil.isNotEmpty(getConditions())) {
            getConditions().forEach(c -> {
                if (Objects.nonNull(c) && CollectionUtil.isNotEmpty(c.getArgs())) {
                    String express = RuleUtils.expression(c.getValuePath(), c.getOperator(), c.getValueType(), c.getArgs());
                    if (StrUtil.isNotBlank(express)) {
                        rules.add(express);
                    }
                }
            });
        }
        return StrUtil.join(" && ", rules);
    }

}
