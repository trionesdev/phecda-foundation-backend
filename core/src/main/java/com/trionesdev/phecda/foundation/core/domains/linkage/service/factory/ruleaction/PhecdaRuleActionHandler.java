package com.trionesdev.phecda.foundation.core.domains.linkage.service.factory.ruleaction;

import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;

public interface PhecdaRuleActionHandler {

    void execute(ActionArgs actionArgs, PhecdaAction phecdaAction);
}
