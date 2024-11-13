package com.trionesdev.phecda.backend.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.commons.exception.BusinessException;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSinkPO;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.RuleSink;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.event.spring.MessageSinkChangeEvent;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.manager.impl.MessageSinkManager;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.manager.impl.RuleSinkManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageSinkService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MessageSinkManager messageSinkManager;
    private final RuleSinkManager ruleSinkManager;

    public void create(MessageSinkPO record) {
        messageSinkManager.create(record);
        applicationEventPublisher.publishEvent(new MessageSinkChangeEvent(this, record));
    }

    public void deleteById(String id) {
        List<RuleSink> ruleSinks = ruleSinkManager.findListBySinkId(id);
        if (CollectionUtil.isNotEmpty(ruleSinks)) {
            throw new BusinessException("MESSAGE_SINK_USED");
        }
        messageSinkManager.findById(id).ifPresent(messageSinkSnap -> {
            messageSinkManager.deleteById(id);
            applicationEventPublisher.publishEvent(new MessageSinkChangeEvent(this, messageSinkSnap));
        });

    }

    public void updateById(MessageSinkPO record) {
        messageSinkManager.updateById(record);
        applicationEventPublisher.publishEvent(new MessageSinkChangeEvent(this, record));
    }

    public Optional<MessageSinkPO> findById(String id) {
        return messageSinkManager.findById(id);
    }

    public List<MessageSinkPO> findList() {
        return messageSinkManager.findList();
    }

}
