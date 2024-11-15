package com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl.*;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourceTopicPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.MessageForwardingDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root.MessageForwardingRuleAggregate;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.criteria.MessageForwardingRuleCriteria;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSource;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageForwardingRuleManager {
    private final MessageForwardingDomainConvert convert;
    private final MessageForwardingRuleDAO messageForwardingRuleDAO;
    private final RuleSourceDAO ruleSourceDAO;
    private final RuleSinkDAO ruleSinkDAO;
    private final MessageSourceTopicDAO messageSourceTopicDAO;
    private final MessageSourceDAO messageSourceDAO;
    private final MessageSinkDAO messageSinkDAO;

    public void create(MessageForwardingRulePO record) {
        messageForwardingRuleDAO.save(record);
    }

    public void deleteById(String id) {
        messageForwardingRuleDAO.removeById(id);
    }

    public void updateById(MessageForwardingRulePO record) {
        messageForwardingRuleDAO.updateById(record);
    }

    public Optional<MessageForwardingRulePO> findById(String id) {
        return Optional.ofNullable(messageForwardingRuleDAO.getById(id));
    }

    public List<MessageForwardingRulePO> findList() {
        return messageForwardingRuleDAO.list();
    }

    /**
     * 获取全部激活的转发规则
     *
     * @return
     */
    public List<MessageForwardingRuleAggregate> activeForwardingList() {
        List<MessageForwardingRulePO> messageForwardingRulePOS = messageForwardingRuleDAO.selectList(MessageForwardingRuleCriteria.builder().enabled(true).build());
        if (CollectionUtil.isEmpty(messageForwardingRulePOS)) {
            return Collections.emptyList();
        }
        Set<String> ruleIds = messageForwardingRulePOS.stream().map(MessageForwardingRulePO::getId).collect(Collectors.toSet());

        MultiValuedMap<String, String> ruleSourceMap = new HashSetValuedHashMap<>();
        Set<String> sourceIds = new HashSet<>();
        ruleSourceDAO.selectByRuleIds(ruleIds).forEach(ruleSource -> {
            ruleSourceMap.put(ruleSource.getRuleId(), ruleSource.getSourceId());
            sourceIds.add(ruleSource.getSourceId());
        });

        MultiValuedMap<String, String> ruleSinkMap = new HashSetValuedHashMap<>();
        Set<String> sinkIds = new HashSet<>();
        ruleSinkDAO.selectListByRuleIds(ruleIds).forEach(ruleSink -> {
            ruleSinkMap.put(ruleSink.getRuleId(), ruleSink.getSinkId());
            sinkIds.add(ruleSink.getSinkId());
        });

        List<MessageSourceTopicPO> sourceTopics = messageSourceTopicDAO.selectListBySourceIds(sourceIds);

        Map<String, MessageSource> messageSourceMap = messageSourceDAO.selectListByIds(sourceIds).stream().map(source -> {
            List<MessageSource.Topic> topics = sourceTopics.stream().filter(topic -> topic.getSourceId().equals(source.getId())).map(topic -> MessageSource.Topic.builder().id(topic.getId()).topic(topic.getTopic()).build()).collect(Collectors.toList());
            return MessageSource.builder()
                    .id(source.getId())
                    .topics(topics)
                    .build();
        }).collect(Collectors.toMap(MessageSource::getId, source -> source, (v1, v2) -> v1));
        MultiValuedMap<String, MessageSink> ruleMessageSinksMap = new HashSetValuedHashMap<>();
        List<MessageSink> messageSinkPOS = messageSinkDAO.selectListByIds(sinkIds).stream().map(t -> {
            var sink = convert.messageSinkPoToEntity(t);
            sink.getAction().setId(t.getId());
            return sink;
        }).toList();
        ruleSinkMap.asMap().forEach((k, v) -> ruleMessageSinksMap.putAll(k, messageSinkPOS.stream().filter(messageSink -> CollectionUtil.contains(v, messageSink.getId())).collect(Collectors.toList())));

        return messageForwardingRulePOS.stream().map(rule -> {

            MessageSource messageSourceDTO = messageSourceMap.get(Optional.ofNullable(ruleSourceMap.get(rule.getId())).flatMap(t -> t.stream().findFirst()).orElse(null));

            return MessageForwardingRuleAggregate.builder()
                    .id(rule.getId())
                    .name(rule.getName())
                    .description(rule.getDescription())
                    .enabled(rule.getEnabled())
                    .source(messageSourceDTO)
                    .sinks(ruleMessageSinksMap.get(rule.getId()))
                    .build();
        }).collect(Collectors.toList());

    }

}
