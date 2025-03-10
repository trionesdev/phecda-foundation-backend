package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal;

import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl.MessageSinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

import static com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.EventConstants.MESSAGE_FORWARDING_RULE_CHANGE;
import static com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.EventConstants.MESSAGE_SINK_CHANGE;

@RequiredArgsConstructor
@Configuration
public class MessageForwardingBootstrap implements ApplicationRunner {
    private final MessageContainer messageContainer;
    private final ForwardingActionFactory forwardingActionFactory;
    private final MessageSinkService messageSinkService;

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            messageContainer.addBroadcastListener(message -> forwardingActionFactory.syncMessageForwardingRules(), MESSAGE_FORWARDING_RULE_CHANGE);

            messageContainer.addBroadcastListener(message -> {
                String sinkId = message.getPayload();
                messageSinkService.syncSinkActions(sinkId);
            }, MESSAGE_SINK_CHANGE);
        });
    }
}
