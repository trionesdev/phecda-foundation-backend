package ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.impl;

import cn.hutool.core.map.MapUtil;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.PhecdaRuleAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.support.rule.action.NotificationAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction.TypeEnum;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaRuleActionComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@PhecdaRuleActionComponent(type = TypeEnum.NOTIFICATION)
public class NotificationActionImpl implements PhecdaRuleAction {
    @Override
    public void execute(ActionArgs actionArgs, PhecdaAction phecdaAction) {
        NotificationAction notificationAction = (NotificationAction) phecdaAction;
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("productId", actionArgs.getProductId());
        paramsMap.put("productKey", actionArgs.getProductKey());
        paramsMap.put("deviceId", actionArgs.getDeviceId());
        paramsMap.put("deviceName", actionArgs.getDeviceName());
        if (MapUtil.isNotEmpty(actionArgs.getReadings())) {
            String content = actionArgs.getReadings().values().stream().map(reading -> reading.getLabel() + ":" + reading.getValue().toString()).collect(Collectors.joining(","));
            paramsMap.put("content", content);
        }


    }
}
