package com.trionesdev.phecda.foundation.core.domains.linkage.internal.factory.ruleaction;

import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;

public interface PhecdaRuleAction {

    void execute(ActionArgs actionArgs, PhecdaAction phecdaAction);
}
