package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.EventConstants.MESSAGE_FORWARDING_RULE_CHANGE;
import static com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.EventConstants.MESSAGE_SINK_CHANGE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageForwardingListener {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 规则变更，通过redis广播通知所有订阅者
     *
     * @param event
     */
    @EventListener(MessageForwardingRuleChangeEvent.class)
    public void ruleChange(MessageForwardingRuleChangeEvent event) {
        stringRedisTemplate.convertAndSend(MESSAGE_FORWARDING_RULE_CHANGE, event.getRule().getId());
        log.info("[MessageForwardingListener#ruleChange] rule change {}", event.getRule().getName());
    }

    /**
     * 落库行为变更，通过redis广播通知所有订阅者
     *
     * @param event
     */
    @EventListener(MessageSinkChangeEvent.class)
    public void sinkActionChange(MessageSinkChangeEvent event) {
        stringRedisTemplate.convertAndSend(MESSAGE_SINK_CHANGE, event.getMessageSinkPO().getId());
        log.info("[MessageForwardingListener#sinkActionChange] sink action change {}", event.getMessageSinkPO().getName());
    }

}
