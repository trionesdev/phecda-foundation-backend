package ms.phecda.backend.core.domains.linkage.support.rule.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.Lists;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("THING_MODEL_PROPERTY_EXPORT")
public class ThingModelExportCondition extends FilterCondition {
    private String product;
    private String deviceName;
    private String property;
    private OperatorEnum operator;

    @Override
    public String toConditionEl() {
        List<String> conditions = Lists.newArrayList();
        if (StrUtil.isNotBlank(product)) {
            conditions.add(" product == " + param(product, true));
        }
        if (StrUtil.isNotBlank(deviceName)) {
            conditions.add(" deviceName == " + param(deviceName, true));
        }
        if (StrUtil.isNotBlank(property)) {
            switch (operator) {
                case GT:
                    conditions.add(property + " > " + param(getParams().get(0)));
                    break;
                case GE:
                    conditions.add(property + " >= " + param(getParams().get(0)));
                    break;
                case LT:
                    conditions.add(property + " < " + param(getParams().get(0)));
                    break;
                case LE:
                    conditions.add(property + " <= " + param(getParams().get(0)));
                    break;
                case BETWEEN:
                    conditions.add(param(getParams().get(0)) + "< " + property + " < " + param(getParams().get(1)));
                    break;
                case BETWEEN_EQ:
                    conditions.add(param(getParams().get(0)) + "<= " + property + " <= " + param(getParams().get(1)));
                    break;
                case EQ:
                default:
                    conditions.add(property + " == " + param(getParams().get(0), true));
                    break;
            }
        }
        return StrUtil.join(" && ", conditions);
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
