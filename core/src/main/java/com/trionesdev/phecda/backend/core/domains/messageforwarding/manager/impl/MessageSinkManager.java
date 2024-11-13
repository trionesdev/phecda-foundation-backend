package com.trionesdev.phecda.backend.core.domains.messageforwarding.manager.impl;

import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.enums.SinkActionType;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.impl.MessageSinkDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageSinkManager {
    private final MessageSinkDAO messageSinkDAO;

    public void create(MessageSinkPO record) {
        messageSinkDAO.save(record);
    }

    public void deleteById(String id) {
        messageSinkDAO.removeById(id);
    }

    public void updateById(MessageSinkPO record) {
        messageSinkDAO.updateById(record);
    }

    public Optional<MessageSinkPO> findById(String id) {
        return Optional.ofNullable(messageSinkDAO.getById(id));
    }

    public List<MessageSinkPO> findList() {
        return messageSinkDAO.list();
    }

    public List<MessageSinkPO> findOnlineByType(SinkActionType type) {
//        MessageSink messageSink = MessageSink.builder()
//                .id("121212")
//                .type(SinkAction.TypeEnum.KAFKA)
//                .action(KafkaSinkAction.builder().bootstrapServers("192.168.10.206:9092").topic("kafka-test").build())
//                .build();
//        return Lists.newArrayList(messageSink);
        return messageSinkDAO.selectListByType(type);
    }

    public List<MessageSinkPO> findListByIds(Collection<String> ids) {
        return messageSinkDAO.listByIds(ids);
    }

}
