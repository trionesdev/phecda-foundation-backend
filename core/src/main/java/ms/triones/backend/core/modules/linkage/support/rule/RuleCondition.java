package ms.triones.backend.core.modules.linkage.support.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class RuleCondition {
    private List<Object> params;
    protected String param(Object param) {
        return param(param, false);
    }

    protected String param(Object param, Boolean str) {
        if (str) {
            return "\"" + param + "\"";
        } else {
            return String.valueOf(param);
        }
    }

    public abstract String toConditionEl();
}
