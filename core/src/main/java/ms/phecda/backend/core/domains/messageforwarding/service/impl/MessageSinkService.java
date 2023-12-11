package ms.phecda.backend.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.moensun.commons.exception.spring.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.RuleSink;
import ms.phecda.backend.core.domains.messageforwarding.mq.spring.MessageSinkChangeEvent;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.MessageSinkManager;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.RuleSinkManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageSinkService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MessageSinkManager messageSinkManager;
    private final RuleSinkManager ruleSinkManager;

    public void create(MessageSink record) {
        messageSinkManager.create(record);
        applicationEventPublisher.publishEvent(new MessageSinkChangeEvent(this, record));
    }

    public void deleteById(String id) {
        List<RuleSink> ruleSinks = ruleSinkManager.findListBySinkId(id);
        if (CollectionUtil.isNotEmpty(ruleSinks)) {
            throw new BusinessException("MESSAGE_SINK_USED");
        }
        MessageSink messageSinkSnap = messageSinkManager.findById(id);
        messageSinkManager.deleteById(id);
        applicationEventPublisher.publishEvent(new MessageSinkChangeEvent(this, messageSinkSnap));
    }

    public void updateById(MessageSink record) {
        messageSinkManager.updateById(record);
        applicationEventPublisher.publishEvent(new MessageSinkChangeEvent(this, record));
    }

    public MessageSink findById(String id) {
        return messageSinkManager.findById(id);
    }

    public List<MessageSink> findList() {
        return messageSinkManager.findList();
    }

}
