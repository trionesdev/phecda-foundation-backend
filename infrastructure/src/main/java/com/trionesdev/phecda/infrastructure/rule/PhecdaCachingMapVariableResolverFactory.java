package com.trionesdev.phecda.infrastructure.rule;

import org.mvel2.integration.impl.CachingMapVariableResolverFactory;

import java.util.Map;

public class PhecdaCachingMapVariableResolverFactory extends CachingMapVariableResolverFactory {
    public PhecdaCachingMapVariableResolverFactory(Map variables) {
        super(variables);
    }

    @Override
    public boolean isResolveable(String name) {
        if (!super.isResolveable(name)) {
            super.createVariable(name, null);
        }
        return true;
    }
}
