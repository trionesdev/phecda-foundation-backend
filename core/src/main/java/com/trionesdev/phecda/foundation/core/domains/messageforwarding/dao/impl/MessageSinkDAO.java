package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.enums.SinkActionType;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.mapper.MessageSinkMapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class MessageSinkDAO extends ServiceImpl<MessageSinkMapper, MessageSinkPO> {

    private List<MessageSinkPO> wrapper(List<MessageSinkPO> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return records.stream().peek(t -> Optional.ofNullable(t.getAction()).ifPresent(sinkAction -> sinkAction.setId(t.getId()))).collect(Collectors.toList());
    }

    public List<MessageSinkPO> selectListByType(SinkActionType type) {
        return wrapper(baseMapper.selectList(new LambdaQueryWrapper<MessageSinkPO>().eq(MessageSinkPO::getType, type)));
    }

    public List<MessageSinkPO> selectListByIds(Collection<String> ids) {
        return wrapper(baseMapper.selectList(new LambdaQueryWrapper<MessageSinkPO>().in(MessageSinkPO::getId, ids)));
    }


}