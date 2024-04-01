package ms.phecda.backend.core.domains.messageforwarding.event.redis;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RuleChangeListener implements MessageListener {
    private final ForwardingActionFactory forwardingActionFactory;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        forwardingActionFactory.syncMessageForwardingRules();
    }
}
