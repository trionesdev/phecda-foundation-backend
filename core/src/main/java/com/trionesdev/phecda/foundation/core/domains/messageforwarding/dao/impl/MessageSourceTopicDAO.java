package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourceTopicPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.mapper.MessageSourceTopicMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class MessageSourceTopicDAO extends ServiceImpl<MessageSourceTopicMapper, MessageSourceTopicPO> {

    public List<MessageSourceTopicPO> selectListBySourceId(String sourceId) {
        return baseMapper.selectList(new LambdaQueryWrapper<MessageSourceTopicPO>().eq(MessageSourceTopicPO::getSourceId, sourceId));
    }

    public List<MessageSourceTopicPO> selectListBySourceIds(Collection<String> sourceIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<MessageSourceTopicPO>().in(MessageSourceTopicPO::getSourceId, sourceIds));
    }

}
