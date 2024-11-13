package com.trionesdev.phecda.backend.core.domains.linkage.internal.rule;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.BasicRule;
import org.jeasy.rules.mvel.MVELAction;
import org.jeasy.rules.mvel.MVELCondition;
import org.mvel2.ParserContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class PhecdaRule extends BasicRule {
    private Condition condition = Condition.FALSE;
    private final List<Action> actions = new ArrayList<>();
    private final ParserContext parserContext;

    /**
     * Create a new MVEL rule.
     */
    public PhecdaRule() {
        this(new ParserContext());
    }

    /**
     * Create a new MVEL rule.
     *
     * @param parserContext used to parse condition/action expressions
     */
    public PhecdaRule(ParserContext parserContext) {
        super(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
        this.parserContext = parserContext;
    }

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return this rule
     */
    public PhecdaRule name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public PhecdaRule description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return this rule
     */
    public PhecdaRule priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Specify the rule's condition as MVEL expression.
     * @param condition of the rule
     * @return this rule
     */
    public PhecdaRule when(String condition) {
        this.condition = new MVELCondition(condition, parserContext);
        return this;
    }

    /**
     * Add an action specified as an MVEL expression to the rule.
     * @param action to add to the rule
     * @return this rule
     */
    public PhecdaRule then(String action) {
        this.actions.add(new MVELAction(action, parserContext));
        return this;
    }

    public PhecdaRule then(Action action) {
        this.actions.add(action);
        return this;
    }

    @Override
    public boolean evaluate(Facts facts) {
        return condition.evaluate(facts);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        Map<String, Class> inputs = parserContext.getInputs();
        if (Objects.nonNull(inputs)) {
            for (Entry<String, Class> entry : inputs.entrySet()) {
                if (Objects.isNull(facts.getFact(entry.getKey()))) {
                    facts.put(entry.getKey(), "nil");
                }
            }
        }
        //此处的action 对应的是 rule 中的when
        for (Action action : actions) {
            action.execute(facts);
        }
    }
}
