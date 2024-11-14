package com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.Scene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;

import java.util.List;

@Data
public class LinkageSceneRuleRO {

    //    private FilterCondition filterCondition;
//    private List<List<OtherCondition>> conditions;
    private List<Scene> scenes;
    private ActionTrigger actionTrigger;
    private List<PhecdaAction> actions;
}
