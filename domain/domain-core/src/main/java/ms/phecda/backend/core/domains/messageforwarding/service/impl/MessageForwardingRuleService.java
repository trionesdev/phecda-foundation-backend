package ms.phecda.backend.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import com.trionesdev.commons.exception.spring.ex.NotFoundException;
import lombok.RequiredArgsConstructor;

import ms.phecda.backend.core.domains.messageforwarding.dao.entity.*;
import ms.phecda.backend.core.domains.messageforwarding.internal.event.spring.MessageForwardingRuleChangeEvent;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageForwardingRuleService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MessageForwardingRuleManager messageForwardingRuleManager;
    private final RuleSourceManager ruleSourceManager;
    private final RuleSinkManager ruleSinkManager;
    private final MessageSourceManager messageSourceManager;
    private final MessageSinkManager messageSinkManager;

    public void create(MessageForwardingRule record) {
        messageForwardingRuleManager.create(record);
    }

    public void deleteById(String id) {
        messageForwardingRuleManager.deleteById(id);
    }

    public void updateById(MessageForwardingRule record) {
        messageForwardingRuleManager.updateById(record);
    }

    public Optional<MessageForwardingRule> findById(String id) {
        return messageForwardingRuleManager.findById(id);
    }

    public List<MessageForwardingRule> findList() {
        return messageForwardingRuleManager.findList();
    }

    public void changeEnable(String id, Boolean enabled) {
        MessageForwardingRule ruleSnap = messageForwardingRuleManager.findById(id).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        MessageForwardingRule rule = MessageForwardingRule.builder().id(id).enabled(enabled).build();
        messageForwardingRuleManager.updateById(rule);
        applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
    }

    public void addRuleSource(RuleSource ruleSource) {
        MessageForwardingRule ruleSnap = messageForwardingRuleManager.findById(ruleSource.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSourceManager.create(ruleSource);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public void deleteRuleSource(RuleSource ruleSource) {
        MessageForwardingRule ruleSnap = messageForwardingRuleManager.findById(ruleSource.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSourceManager.delete(ruleSource);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public List<MessageSource> findRuleSources(String ruleId) {
        List<RuleSource> ruleSources = ruleSourceManager.findListByRuleId(ruleId);
        if (CollectionUtil.isEmpty(ruleSources)) {
            return Collections.emptyList();
        }
        return messageSourceManager.findListByIds(ruleSources.stream().map(RuleSource::getSourceId).collect(Collectors.toList()));
    }

    public void addRuleSink(RuleSink ruleSink) {
        MessageForwardingRule ruleSnap = messageForwardingRuleManager.findById(ruleSink.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSinkManager.create(ruleSink);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public void deleteRuleSink(RuleSink ruleSink) {
        MessageForwardingRule ruleSnap = messageForwardingRuleManager.findById(ruleSink.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSinkManager.delete(ruleSink);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public List<MessageSink> findRuleSinks(String ruleId) {
        List<RuleSink> ruleSinks = ruleSinkManager.findListByRuleId(ruleId);
        if (CollectionUtil.isEmpty(ruleSinks)) {
            return Collections.emptyList();
        }
        return messageSinkManager.findListByIds(ruleSinks.stream().map(RuleSink::getSinkId).collect(Collectors.toList()));
    }
}
