package ms.triones.backend.core.modules.linkage.support.rule.othercondition;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("THING_MODEL_PROPERTY")
public class ThingModelPropertyCondition extends OtherCondition {
    private String product;
    private String deviceName;
    private String property;
    private OperatorEnum operator;

    @Override
    public String toConditionEl() {
        switch (operator) {
            case GT:
                return property + " > " + param(getParams().get(0));
            case GE:
                return property + " >= " + param(getParams().get(0));
            case LT:
                return property + " < " + param(getParams().get(0));
            case LE:
                return property + " <= " + param(getParams().get(0));
            case BETWEEN:
                return param(getParams().get(0)) + "< " + property + " < " + param(getParams().get(1));
            case BETWEEN_EQ:
                return param(getParams().get(0)) + "<= " + property + " <= " + param(getParams().get(1));
            case EQ:
            default:
                return property + " == " + param(getParams().get(0), true);
        }
    }

    @AllArgsConstructor
    public enum OperatorEnum {
        EQ("等于"),
        GT("大于"),
        GE("大于等于"),
        LT("小于"),
        LE("小于等于"),
        BETWEEN("闭区间(a,b)"),
        BETWEEN_EQ("开区间[a,b]");

        @Getter
        private final String label;
    }


}
