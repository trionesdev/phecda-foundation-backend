package ms.phecda.backend.core.modules.linkage.service.factory.ruleaction;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.linkage.support.rule.action.Action;
import ms.phecda.backend.core.modules.linkage.support.rule.action.PhecdaRuleActionComponent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class RuleActionFactory {
    private final Map<Action.TypeEnum, PhecdaRuleAction> actionMap = new HashMap<>();

    private final List<PhecdaRuleAction> actions;

    @PostConstruct
    public void init() {
        if (CollectionUtil.isNotEmpty(actions)) {
            actions.forEach(action -> {
                PhecdaRuleActionComponent component = AnnotationUtils.getAnnotation(action.getClass(), PhecdaRuleActionComponent.class);
                if (Objects.nonNull(component)) {
                    actionMap.put(component.type(), action);
                }
            });
        }
    }

    public PhecdaRuleAction getAction(Action.TypeEnum type) {
        return actionMap.get(type);
    }

}
