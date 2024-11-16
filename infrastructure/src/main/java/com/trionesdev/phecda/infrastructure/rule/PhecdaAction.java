package com.trionesdev.phecda.infrastructure.rule;

import org.jeasy.rules.api.Facts;

@FunctionalInterface
public interface PhecdaAction {
    <T> void accept(Facts facts,T content) throws Exception;
}
