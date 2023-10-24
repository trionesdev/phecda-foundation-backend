package ms.phecda.backend.core.domains.linkage.support.rule.trigger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.linkage.support.rule.OperatorEnum;
import ms.phecda.backend.core.domains.linkage.support.util.RuleUtils;
import ms.phecda.edge.base.commons.valuetype.ValueTypeEnum;

import java.util.List;
import java.util.Objects;

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
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = ThingPropertyReportTrigger.class, name = "THING_PROPERTY_REPORT")
        }
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class EventTrigger {
    private TriggerTypeEnum type;
    private Identifier identifier;
    private EventFilter filter;

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
    @JsonSubTypes(
            {
                    @JsonSubTypes.Type(value = ThingPropertyReportTrigger.Identifier.class, name = "THING_PROPERTY_REPORT")
            }
    )
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Identifier {
        private TriggerTypeEnum type;
    }

    @Data
    @Accessors(chain = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EventFilter {
        private OperatorEnum operator;
        private ValueTypeEnum valueType;
        private List<String> args;
    }

    public abstract String toRuleEl();

    public String filterEl(String face) {
        EventFilter eventFilter = getFilter();
        if (Objects.nonNull(eventFilter)) {
            return RuleUtils.expression(face, filter.getOperator(), filter.getValueType(), filter.getArgs());
        }
        return "";
    }

}
