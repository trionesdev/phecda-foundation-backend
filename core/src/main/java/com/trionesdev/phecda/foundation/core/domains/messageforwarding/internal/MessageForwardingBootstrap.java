package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal;

import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.redis.RuleChangeListener;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.redis.SinkActionChangeListener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CompletableFuture;

import static com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.EventConstants.MESSAGE_FORWARDING_RULE_CHANGE;
import static com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.EventConstants.MESSAGE_SINK_CHANGE;

@RequiredArgsConstructor
@Configuration
public class MessageForwardingBootstrap implements ApplicationRunner {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RuleChangeListener ruleChangeListener;
    private final SinkActionChangeListener sinkActionChangeListener;
    private final MessageContainer messageContainer;
    private final ForwardingActionFactory forwardingActionFactory;

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            redisMessageListenerContainer.addMessageListener(new MessageListenerAdapter(ruleChangeListener, "onMessage"), PatternTopic.of(MESSAGE_FORWARDING_RULE_CHANGE));
            redisMessageListenerContainer.addMessageListener(new MessageListenerAdapter(sinkActionChangeListener, "onMessage"), PatternTopic.of(MESSAGE_SINK_CHANGE));

//            messageContainer.addBroadcastListener(new MessageListener() {
//                @Override
//                public void onMessage(Message message) {
//                    forwardingActionFactory.syncMessageForwardingRules();
//                }
//            }, MESSAGE_FORWARDING_RULE_CHANGE);
//
//            messageContainer.addBroadcastListener(new MessageListener() {
//                @Override
//                public void onMessage(Message message) {
//                    String id = message.getPayload();
//                }
//            }, MESSAGE_SINK_CHANGE);
        });
    }
}
