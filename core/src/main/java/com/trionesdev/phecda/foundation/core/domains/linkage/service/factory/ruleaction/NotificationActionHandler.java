package com.trionesdev.phecda.foundation.core.domains.linkage.service.factory.ruleaction;

import cn.hutool.core.map.MapUtil;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.NotificationAction;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction.TypeEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaRuleActionComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@PhecdaRuleActionComponent(type = TypeEnum.NOTIFICATION)
public class NotificationActionHandler implements PhecdaRuleActionHandler {
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
