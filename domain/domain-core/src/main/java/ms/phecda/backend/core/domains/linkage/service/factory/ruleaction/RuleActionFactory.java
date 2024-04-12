package ms.phecda.backend.core.domains.linkage.service.factory.ruleaction;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.linkage.support.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaRuleActionComponent;
import ms.phecda.backend.core.provider.ssp.device.impl.DeviceProvider;
import ms.phecda.backend.core.provider.ssp.device.pdo.thingmodel.ThingModelPropertyPDO;
import org.jeasy.rules.api.Facts;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ms.phecda.backend.core.domains.linkage.support.rule.RuleConstants.*;

@RequiredArgsConstructor
@Component
public class RuleActionFactory {
    private final DeviceProvider deviceProvider;
    private final Map<PhecdaAction.TypeEnum, PhecdaRuleAction> actionMap = new HashMap<>();

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

    public PhecdaRuleAction getAction(PhecdaAction.TypeEnum type) {
        return actionMap.get(type);
    }


    public ActionArgs factsToActionArgs(Facts facts) {
        Map<String, ActionArgs.Reading> readingMap = facts.get(FACT_READINGS);
        if (MapUtil.isNotEmpty(readingMap)) {
            readingMap.forEach((k, v) -> {
                ThingModelPropertyPDO property = deviceProvider.findThingModelPropertyByKey(facts.get(FACT_PRODUCT_KEY), v.getIdentifier());
                if (Objects.nonNull(property)) {
                    v.setLabel(property.getName());
                }
            });
        }
        return ActionArgs.builder()
                .ruleName(facts.get(FACT_RULE_NAME))
                .productId(facts.get(FACT_PRODUCT_ID))
                .productKey(facts.get(FACT_PRODUCT_KEY))
                .deviceId(facts.get(FACT_DEVICE_ID))
                .deviceName(facts.get(FACT_DEVICE_NAME))
                .readings(readingMap)
                .build();
    }
}
