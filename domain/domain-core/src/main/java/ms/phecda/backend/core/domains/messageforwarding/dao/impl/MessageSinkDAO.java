package ms.phecda.backend.core.domains.messageforwarding.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction.SinkAction;
import ms.phecda.backend.core.domains.messageforwarding.dao.mapper.MessageSinkMapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class MessageSinkDAO extends ServiceImpl<MessageSinkMapper, MessageSink> {

    private List<MessageSink> wrapper(List<MessageSink> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return records.stream().peek(t -> Optional.ofNullable(t.getAction()).ifPresent(sinkAction -> sinkAction.setId(t.getId()))).collect(Collectors.toList());
    }

    public List<MessageSink> selectListByType(SinkAction.TypeEnum type) {
        return wrapper(baseMapper.selectList(new LambdaQueryWrapper<MessageSink>().eq(MessageSink::getType, type)));
    }

    public List<MessageSink> selectListByIds(Collection<String> ids) {
        return wrapper(baseMapper.selectList(new LambdaQueryWrapper<MessageSink>().in(MessageSink::getId, ids)));
    }


}