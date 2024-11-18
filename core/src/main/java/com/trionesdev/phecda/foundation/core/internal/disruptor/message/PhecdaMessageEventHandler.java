package com.trionesdev.phecda.foundation.core.internal.disruptor.message;

import com.lmax.disruptor.EventHandler;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl.MessageForwardingRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhecdaMessageEventHandler implements EventHandler<PhecdaMessageEvent> {
    private final MessageForwardingRuleService messageForwardingRuleService;

    @Override
    public void onEvent(PhecdaMessageEvent event, long sequence, boolean endOfBatch) throws Exception {
        //region message forwarding
        messageForwardingRuleService.fireForwards(event.getTopic(), event.getMessage());
        //endregion
    }
}
