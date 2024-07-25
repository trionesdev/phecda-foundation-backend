package ms.phecda.backend.core.domains.messageforwarding.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.criteria.MessageForwardingRuleCriteria;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import ms.phecda.backend.core.domains.messageforwarding.dao.impl.*;
import ms.phecda.backend.core.domains.messageforwarding.dto.MessageForwardingRuleDTO;
import ms.phecda.backend.core.domains.messageforwarding.dto.MessageSourceDTO;
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

    public void create(MessageForwardingRule record) {
        messageForwardingRuleDAO.save(record);
    }

    public void deleteById(String id) {
        messageForwardingRuleDAO.removeById(id);
    }

    public void updateById(MessageForwardingRule record) {
        messageForwardingRuleDAO.updateById(record);
    }

    public Optional<MessageForwardingRule> findById(String id) {
        return Optional.ofNullable(messageForwardingRuleDAO.getById(id));
    }

    public List<MessageForwardingRule> findList() {
        return messageForwardingRuleDAO.list();
    }

    public List<MessageForwardingRuleDTO> activeForwardingList() {
        List<MessageForwardingRuleDTO> messageForwardingList = new ArrayList<>();
        List<MessageForwardingRule> messageForwardingRules = messageForwardingRuleDAO.selectList(MessageForwardingRuleCriteria.builder().enabled(true).build());
        if (CollectionUtil.isEmpty(messageForwardingRules)) {
            return Collections.emptyList();
        }
        Set<String> ruleIds = messageForwardingRules.stream().map(MessageForwardingRule::getId).collect(Collectors.toSet());
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
        MultiValuedMap<String, MessageSink> ruleMessageSinksMap = new HashSetValuedHashMap<>();
        List<MessageSink> messageSinks = messageSinkDAO.selectListByIds(sinkIds).stream().peek(t-> t.getAction().setId(t.getId())).collect(Collectors.toList());
        ruleSinkMap.asMap().forEach((k, v) -> ruleMessageSinksMap.putAll(k, messageSinks.stream().filter(messageSink -> CollectionUtil.contains(v, messageSink.getId())).collect(Collectors.toList())));

       return messageForwardingRules.stream().map(rule -> {

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
