package ms.phecda.backend.core.domains.linkage.internal.rule;

public class RuleUtils {

    public static String ruleContinuousKey(String id) {
        return "phecda:continuous:" + id;
    }

    public static String ruleIntervalKey(String id) {
        return "phecda:interval:" + id;
    }
}
