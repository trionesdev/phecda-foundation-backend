package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.commons.exception.BusinessException;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.RuleSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageSinkManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.RuleSinkManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.KafkaForwardingAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.EventConstants.MESSAGE_SINK_CHANGE;

@RequiredArgsConstructor
@Service
public class MessageSinkService {
    private final MessageContainer messageContainer;
    private final MessageSinkManager messageSinkManager;
    private final RuleSinkManager ruleSinkManager;
    private final KafkaForwardingAction kafkaForwardingAction;

    public void create(MessageSinkPO record) {
        messageSinkManager.create(record);
        messageContainer.broadcast(MESSAGE_SINK_CHANGE, record.getId());
    }

    public void deleteById(String id) {
        List<RuleSinkPO> ruleSinks = ruleSinkManager.findListBySinkId(id);
        if (CollectionUtil.isNotEmpty(ruleSinks)) {
            throw new BusinessException("MESSAGE_SINK_USED");
        }
        messageSinkManager.findById(id).ifPresent(messageSinkSnap -> {
            messageSinkManager.deleteById(id);
            messageContainer.broadcast(MESSAGE_SINK_CHANGE, messageSinkSnap.getId());
        });

    }

    public void updateById(MessageSinkPO record) {
        messageSinkManager.updateById(record);
        messageContainer.broadcast(MESSAGE_SINK_CHANGE, record.getId());
    }

    public Optional<MessageSinkPO> findById(String id) {
        return messageSinkManager.findById(id);
    }

    public List<MessageSinkPO> findList() {
        return messageSinkManager.findList();
    }

    public void syncSinkActions(String id) {
        messageSinkManager.findById(id).ifPresent(messageSink -> {
            if (Objects.isNull(messageSink.getAction())) {
                return;
            }
            switch (messageSink.getAction().getType()) {
                case KAFKA:
                    kafkaForwardingAction.kafkaSync();
                    break;
            }
        });
    }

}
