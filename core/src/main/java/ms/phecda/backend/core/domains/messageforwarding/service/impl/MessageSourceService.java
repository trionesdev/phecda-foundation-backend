package ms.phecda.backend.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.commons.exception.spring.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSource;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSourceTopic;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.RuleSource;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.MessageSourceManager;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.MessageSourceTopicManager;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.RuleSourceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageSourceService {
    private final MessageSourceManager messageSourceManager;
    private final MessageSourceTopicManager messageSourceTopicManager;
    private final RuleSourceManager ruleSourceManager;

    public void create(MessageSource record) {
        messageSourceManager.create(record);
    }

    public void deleteById(String id) {
        List<RuleSource> ruleSources = ruleSourceManager.findListBySourceId(id);
        if (CollectionUtil.isNotEmpty(ruleSources)) {
            throw new BusinessException("MESSAGE_SOURCE_USED");
        }
        messageSourceManager.deleteById(id);
    }

    public void updateById(MessageSource record) {
        messageSourceManager.updateById(record);
    }

    public MessageSource findById(String id) {
        return messageSourceManager.findById(id);
    }

    public List<MessageSource> findAll() {
        return messageSourceManager.findAll();
    }

    public void createSourceTopic(MessageSourceTopic sourceTopic) {
        messageSourceTopicManager.create(sourceTopic);
    }

    public List<MessageSourceTopic> findSourceTopics(String sourceId) {
        return messageSourceTopicManager.findSourceTopics(sourceId);
    }

    public void deleteSourceTopic(String sourceId, String topicId) {
        messageSourceTopicManager.deleteById(topicId);
    }

}
