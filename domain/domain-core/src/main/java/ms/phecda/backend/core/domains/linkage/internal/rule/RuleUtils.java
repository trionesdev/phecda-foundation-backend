package ms.phecda.backend.core.domains.linkage.internal.rule;

public class RuleUtils {

    public static String ruleContinuousKey(String id) {
        return "rule:continuous:" + id;
    }

    public static String ruleIntervalKey(String id) {
        return "rule:interval:" + id;
    }
}
