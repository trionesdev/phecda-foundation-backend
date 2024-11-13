package com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.event.spring;

import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSinkPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageSinkChangeEvent extends ApplicationEvent {
    private final String type;
    private final MessageSinkPO messageSinkPO;

    public MessageSinkChangeEvent(Object source, MessageSinkPO messageSinkPO) {
        super(source);
        this.type = "update";
        this.messageSinkPO = messageSinkPO;
    }

    public MessageSinkChangeEvent(Object source, String type, MessageSinkPO messageSinkPO) {
        super(source);
        this.type = type;
        this.messageSinkPO = messageSinkPO;
    }
}
