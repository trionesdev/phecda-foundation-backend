package com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourceTopicPO;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl.MessageSourceTopicDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageSourceTopicManager {
    private final MessageSourceTopicDAO messageSourceTopicDAO;

    public void create(MessageSourceTopicPO record) {
        messageSourceTopicDAO.save(record);
    }

    public List<MessageSourceTopicPO> findSourceTopics(String sourceId) {
        return messageSourceTopicDAO.selectListBySourceId(sourceId);
    }

    public void deleteById(String topicId) {
        messageSourceTopicDAO.removeById(topicId);
    }

}
