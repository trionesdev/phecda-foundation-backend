package ms.triones.backend.rest.backend.modules.linkage.controller.ro;

import lombok.Data;
import ms.triones.backend.core.modules.linkage.support.rule.action.Action;
import ms.triones.backend.core.modules.linkage.support.rule.filter.FilterCondition;
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.OtherCondition;

import java.util.List;

@Data
public class LinkageSceneRuleRO {

    private FilterCondition filterCondition;
    private List<List<OtherCondition>> conditions;
    private List<Action> actions;
}
