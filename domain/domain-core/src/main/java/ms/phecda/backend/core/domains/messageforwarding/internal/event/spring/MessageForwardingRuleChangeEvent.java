package ms.phecda.backend.core.domains.messageforwarding.internal.event.spring;

import lombok.Getter;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageForwardingRule;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageForwardingRuleChangeEvent extends ApplicationEvent {
    private final MessageForwardingRule rule;

    public MessageForwardingRuleChangeEvent(Object source, MessageForwardingRule rule) {
        super(source);
        this.rule = rule;
    }
}
