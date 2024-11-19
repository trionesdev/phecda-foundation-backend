package com.trionesdev.phecda.foundation.core.internal.disruptor.message;

import com.lmax.disruptor.EventHandler;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.DeviceDataService;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.impl.LinkageSceneService;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl.MessageForwardingRuleService;
import com.trionesdev.phecda.foundation.core.internal.util.MqttTopicUtils;
import com.trionesdev.phecda.foundation.core.internal.util.TopicUtils;
import com.trionesdev.phecda.infrastructure.configuration.mqtt.PhecdaMqttProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhecdaMessageEventHandler implements EventHandler<PhecdaMessageEvent> {
    private final PhecdaMqttProperties mqttProperties;
    private final MessageForwardingRuleService messageForwardingRuleService;
    private final LinkageSceneService linkageSceneService;
    private final DeviceDataService deviceDataService;

    @Override
    public void onEvent(PhecdaMessageEvent event, long sequence, boolean endOfBatch) throws Exception {
        //region message forwarding
        messageForwardingRuleService.fireForwards(event.getTopic(), event.getMessage());
        //endregion
        //region linkage scene
        linkageSceneService.fireScenes(event.getMessage());
        //endregion
        String propertyPostTopic = TopicUtils.join(mqttProperties.getTopicPrefix(), TopicUtils.propertyPostTopic(null, null));
        if (MqttTopicUtils.isMatched(propertyPostTopic, event.getTopic())) {
            deviceDataService.savePropertyData(event.getMessage());
        }
    }


}
