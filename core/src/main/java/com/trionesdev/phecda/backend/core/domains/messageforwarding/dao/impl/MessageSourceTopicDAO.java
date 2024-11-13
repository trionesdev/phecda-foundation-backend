package com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.mapper.MessageSourceTopicMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class MessageSourceTopicDAO extends ServiceImpl<MessageSourceTopicMapper, MessageSourceTopic> {

    public List<MessageSourceTopic> selectListBySourceId(String sourceId) {
        return baseMapper.selectList(new LambdaQueryWrapper<MessageSourceTopic>().eq(MessageSourceTopic::getSourceId, sourceId));
    }

    public List<MessageSourceTopic> selectListBySourceIds(Collection<String> sourceIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<MessageSourceTopic>().in(MessageSourceTopic::getSourceId, sourceIds));
    }

}
