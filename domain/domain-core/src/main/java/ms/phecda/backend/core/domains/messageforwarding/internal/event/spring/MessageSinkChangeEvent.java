package ms.phecda.backend.core.domains.messageforwarding.internal.event.spring;

import lombok.Getter;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageSinkChangeEvent extends ApplicationEvent {
    private final String type;
    private final MessageSink messageSink;

    public MessageSinkChangeEvent(Object source, MessageSink messageSink) {
        super(source);
        this.type = "update";
        this.messageSink = messageSink;
    }

    public MessageSinkChangeEvent(Object source, String type, MessageSink messageSink) {
        super(source);
        this.type = type;
        this.messageSink = messageSink;
    }
}
