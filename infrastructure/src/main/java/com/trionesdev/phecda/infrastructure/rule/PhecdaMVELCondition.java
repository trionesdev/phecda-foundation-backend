package com.trionesdev.phecda.infrastructure.rule;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.mvel.MVELCondition;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

import java.io.Serializable;

public class PhecdaMVELCondition implements Condition {

    private final Serializable compiledExpression;

    /**
     * Create a new {@link MVELCondition}.
     *
     * @param expression the condition written in expression language
     */
    public PhecdaMVELCondition(String expression) {
        compiledExpression = MVEL.compileExpression(expression);
    }

    /**
     * Create a new {@link MVELCondition}.
     *
     * @param expression the condition written in expression language
     * @param parserContext the MVEL parser context
     */
    public PhecdaMVELCondition(String expression, ParserContext parserContext) {
        compiledExpression = MVEL.compileExpression(expression, parserContext);
    }

    @Override
    public boolean evaluate(Facts facts) {
        // MVEL.evalToBoolean does not accept compiled expressions..
        return (boolean) MVEL.executeExpression(compiledExpression, facts.asMap(),new PhecdaCachingMapVariableResolverFactory(facts.asMap()));
    }

}
