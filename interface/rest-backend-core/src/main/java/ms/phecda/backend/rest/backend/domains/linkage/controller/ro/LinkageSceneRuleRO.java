package ms.phecda.backend.rest.backend.domains.linkage.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.linkage.support.rule.Scene;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction;

import java.util.List;

@Data
public class LinkageSceneRuleRO {

//    private FilterCondition filterCondition;
//    private List<List<OtherCondition>> conditions;
    private List<Scene> scenes;
    private List<PhecdaAction> phecdaActions;
}
