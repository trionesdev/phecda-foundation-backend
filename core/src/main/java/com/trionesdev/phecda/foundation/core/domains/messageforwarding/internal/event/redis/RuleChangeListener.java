package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.redis;

import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.factory.ForwardingActionFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 监听规则改变事件
 */
@RequiredArgsConstructor
@Component
public class RuleChangeListener implements MessageListener {
    private final ForwardingActionFactory forwardingActionFactory;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        forwardingActionFactory.syncMessageForwardingRules();
    }
}
