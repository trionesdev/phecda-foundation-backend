package com.trionesdev.phecda.backend.rest.backend.domains.linkage.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.Scene;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.ActionTrigger;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction;

import java.util.List;

@Data
public class LinkageSceneRuleRO {

    //    private FilterCondition filterCondition;
//    private List<List<OtherCondition>> conditions;
    private List<Scene> scenes;
    private ActionTrigger actionTrigger;
    private List<PhecdaAction> actions;
}
