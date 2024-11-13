package com.trionesdev.phecda.backend.core.domains.messageforwarding.manager.impl;

import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.impl.MessageSourceTopicDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageSourceTopicManager {
    private final MessageSourceTopicDAO messageSourceTopicDAO;

    public void create(MessageSourceTopic record) {
        messageSourceTopicDAO.save(record);
    }

    public List<MessageSourceTopic> findSourceTopics(String sourceId) {
        return messageSourceTopicDAO.selectListBySourceId(sourceId);
    }

    public void deleteById(String topicId) {
        messageSourceTopicDAO.removeById(topicId);
    }

}
