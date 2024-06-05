package ms.phecda.backend.core.domains.linkage.internal.util;

import ms.phecda.backend.core.domains.linkage.internal.rule.OperatorEnum;
import ms.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeEnum;

import java.util.List;

public class RuleUtils {

    public static String param(Object param) {
        return param(param, false);
    }

    public static String param(Object param, Boolean str) {
        if (str) {
            return "\"" + param + "\"";
        } else {
            return String.valueOf(param);
        }
    }

    public static String param(String param, ValueTypeEnum valueType) {
        switch (valueType) {
            case STRING:
                return param(param, true);
            default:
                return param(param);
        }
    }

    public static String expression(String face, OperatorEnum operator, ValueTypeEnum valueType, List<String> args) {
        switch (operator) {
            case EQ:
                return face + " == " + param(args.get(0), valueType);
            case GT:
                return face + " > " + param(args.get(0));
            case GE:
                return face + " >= " + param(args.get(0));
            case LT:
                return face + " < " + param(args.get(0));
            case LE:
                return face + " <= " + param(args.get(0));
            case RANGE_CLOSED:
                return param(args.get(0)) + " <  " + face + " && " + face + " < " + param(args.get(1));
            case RANGE_OPEN:
                return param(args.get(0)) + " <=  " + face + " && " + face + " <= " + param(args.get(1));
            default:
                return "";
        }
    }
}
