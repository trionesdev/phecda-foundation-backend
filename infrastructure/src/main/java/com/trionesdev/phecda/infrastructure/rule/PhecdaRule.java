package com.trionesdev.phecda.infrastructure.rule;

import com.alibaba.fastjson2.JSON;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.BasicRule;
import org.jeasy.rules.mvel.MVELAction;
import org.jeasy.rules.mvel.MVELCondition;
import org.mvel2.ParserConfiguration;
import org.mvel2.ParserContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;

public class PhecdaRule<T> extends BasicRule {
    private Condition condition = Condition.FALSE;
    private final List<Action> actions = new ArrayList<>();
    private final List<BiConsumer<Facts, T>> consumers = new ArrayList<>();
    private final ParserContext parserContext;

    /**
     * Create a new MVEL rule.
     */
    public PhecdaRule() {
//        this(new ParserContext());
        ParserConfiguration parserConfiguration = new ParserConfiguration();
//        parserConfiguration
        this.parserContext = new ParserContext(parserConfiguration);
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
    public PhecdaRule<T> name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public PhecdaRule<T> description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return this rule
     */
    public PhecdaRule<T> priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Specify the rule's condition as MVEL expression.
     *
     * @param condition of the rule
     * @return this rule
     */
    public PhecdaRule<T> when(String condition) {
        this.condition = new PhecdaMVELCondition(condition, parserContext);
        return this;
    }

    public PhecdaRule<T> then(String action) {
        this.actions.add(new MVELAction(action, parserContext));
        return this;
    }

    public PhecdaRule<T> then(BiConsumer<Facts, T> action) {
        this.consumers.add(action);
        return this;
    }

    @Override
    public boolean evaluate(Facts facts) {
        return condition.evaluate(facts);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        Map<String, Class> inputs = parserContext.getInputs();
        System.out.println(JSON.toJSONString(facts));
        if (Objects.nonNull(inputs)) {
            for (Entry<String, Class> entry : inputs.entrySet()) {
                if (Objects.isNull(facts.getFact(entry.getKey()))) {
                    facts.put(entry.getKey(), "nil");
                }
            }
        }
        for (Action action : actions) {
            action.execute(facts);
        }
        //此处的action 对应的是 rule 中的when
        for (BiConsumer<Facts, ?> action : consumers) {
            action.accept(facts, null);
        }
    }

    public void execute(Facts facts, T content) throws Exception {
        Map<String, Class> inputs = parserContext.getInputs();
        System.out.println(JSON.toJSONString(facts));
        //此处的action 对应的是 rule 中的when
        for (Action action : actions) {
            action.execute(facts);
        }
        for (BiConsumer<Facts, T> action : consumers) {
            action.accept(facts, content);
        }
    }

}
