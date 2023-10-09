package ms.phecda.backend.rest.backend.domains.linkage.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.linkage.support.rule.action.Action;
import ms.phecda.backend.core.domains.linkage.support.rule.filter.FilterCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.othercondition.OtherCondition;

import java.util.List;

@Data
public class LinkageSceneRuleRO {

    private FilterCondition filterCondition;
    private List<List<OtherCondition>> conditions;
    private List<Action> actions;
}
