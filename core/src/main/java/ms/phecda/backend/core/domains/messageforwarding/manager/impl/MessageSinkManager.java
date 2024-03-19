package ms.phecda.backend.core.domains.messageforwarding.manager.impl;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction.KafkaSinkAction;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction.SinkAction;
import ms.phecda.backend.core.domains.messageforwarding.dao.impl.MessageSinkDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageSinkManager {
    private final MessageSinkDAO messageSinkDAO;

    public void create(MessageSink record) {
        messageSinkDAO.save(record);
    }

    public void deleteById(String id) {
        messageSinkDAO.removeById(id);
    }

    public void updateById(MessageSink record) {
        messageSinkDAO.updateById(record);
    }

    public Optional<MessageSink> findById(String id) {
        return Optional.ofNullable(messageSinkDAO.getById(id));
    }

    public List<MessageSink> findList() {
        return messageSinkDAO.list();
    }

    public List<MessageSink> findOnlineByType(SinkAction.TypeEnum type) {
//        MessageSink messageSink = MessageSink.builder()
//                .id("121212")
//                .type(SinkAction.TypeEnum.KAFKA)
//                .action(KafkaSinkAction.builder().bootstrapServers("192.168.10.206:9092").topic("kafka-test").build())
//                .build();
//        return Lists.newArrayList(messageSink);
        return messageSinkDAO.selectListByType(type);
    }

    public List<MessageSink> findListByIds(Collection<String> ids) {
        return messageSinkDAO.listByIds(ids);
    }

}
