package com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl.*;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.criteria.MessageForwardingRuleCriteria;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto.MessageForwardingRuleDTO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto.MessageSourceDTO;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageForwardingRuleManager {
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

    public List<MessageForwardingRuleDTO> activeForwardingList() {
        List<MessageForwardingRuleDTO> messageForwardingList = new ArrayList<>();
        List<MessageForwardingRulePO> messageForwardingRulePOS = messageForwardingRuleDAO.selectList(MessageForwardingRuleCriteria.builder().enabled(true).build());
        if (CollectionUtil.isEmpty(messageForwardingRulePOS)) {
            return Collections.emptyList();
        }
        Set<String> ruleIds = messageForwardingRulePOS.stream().map(MessageForwardingRulePO::getId).collect(Collectors.toSet());
//        Set<String> sourceIds = ruleSourceDAO.selectByRuleIds(ruleIds).stream().map(RuleSource::getSourceId).collect(Collectors.toSet());

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

//        Set<String> sinkIds = ruleSinkDAO.selectListByRuleIds(ruleIds).stream().map(RuleSink::getSinkId).collect(Collectors.toSet());
        List<MessageSourceTopic> sourceTopics = messageSourceTopicDAO.selectListBySourceIds(sourceIds);

        Map<String, MessageSourceDTO> messageSourceMap = messageSourceDAO.selectListByIds(sourceIds).stream().map(source -> {
            List<MessageSourceDTO.Topic> topics = sourceTopics.stream().filter(topic -> topic.getSourceId().equals(source.getId())).map(topic -> MessageSourceDTO.Topic.builder().id(topic.getId()).topic(topic.getTopic()).build()).collect(Collectors.toList());
            return MessageSourceDTO.builder()
                    .id(source.getId())
                    .topics(topics)
                    .build();
        }).collect(Collectors.toMap(MessageSourceDTO::getId, source -> source, (v1, v2) -> v1));
//        Map<String, MessageSink> messageSinkMap = messageSinkDAO.selectListByIds(sinkIds).stream().collect(Collectors.toMap(MessageSink::getId, sink -> sink, (v1, v2) -> v1));
        MultiValuedMap<String, MessageSinkPO> ruleMessageSinksMap = new HashSetValuedHashMap<>();
        List<MessageSinkPO> messageSinkPOS = messageSinkDAO.selectListByIds(sinkIds).stream().peek(t-> t.getAction().setId(t.getId())).collect(Collectors.toList());
        ruleSinkMap.asMap().forEach((k, v) -> ruleMessageSinksMap.putAll(k, messageSinkPOS.stream().filter(messageSink -> CollectionUtil.contains(v, messageSink.getId())).collect(Collectors.toList())));

       return messageForwardingRulePOS.stream().map(rule -> {

            MessageSourceDTO messageSourceDTO = messageSourceMap.get(Optional.ofNullable(ruleSourceMap.get(rule.getId())).flatMap(t -> t.stream().findFirst()).orElse(null));

           return MessageForwardingRuleDTO.builder()
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
