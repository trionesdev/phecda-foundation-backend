package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.spring;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageForwardingRuleChangeEvent extends ApplicationEvent {
    private final MessageForwardingRulePO rule;

    public MessageForwardingRuleChangeEvent(Object source, MessageForwardingRulePO rule) {
        super(source);
        this.rule = rule;
    }
}
