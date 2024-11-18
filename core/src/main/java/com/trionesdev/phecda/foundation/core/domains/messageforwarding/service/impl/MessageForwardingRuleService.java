package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import com.trionesdev.commons.exception.NotFoundException;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.*;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.event.spring.MessageForwardingRuleChangeEvent;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.*;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost.PropertiesPostMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageForwardingRuleService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ForwardingActionFactory forwardingActionFactory;
    private final MessageForwardingRuleManager messageForwardingRuleManager;
    private final RuleSourceManager ruleSourceManager;
    private final RuleSinkManager ruleSinkManager;
    private final MessageSourceManager messageSourceManager;
    private final MessageSinkManager messageSinkManager;

    public void create(MessageForwardingRulePO record) {
        messageForwardingRuleManager.create(record);
    }

    public void deleteById(String id) {
        messageForwardingRuleManager.deleteById(id);
    }

    public void updateById(MessageForwardingRulePO record) {
        messageForwardingRuleManager.updateById(record);
    }

    public Optional<MessageForwardingRulePO> findById(String id) {
        return messageForwardingRuleManager.findById(id);
    }

    public List<MessageForwardingRulePO> findList() {
        return messageForwardingRuleManager.findList();
    }

    public void changeEnable(String id, Boolean enabled) {
        MessageForwardingRulePO ruleSnap = messageForwardingRuleManager.findById(id).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        MessageForwardingRulePO rule = MessageForwardingRulePO.builder().id(id).enabled(enabled).build();
        messageForwardingRuleManager.updateById(rule);
        applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
    }

    public void addRuleSource(RuleSourcePO ruleSource) {
        MessageForwardingRulePO ruleSnap = messageForwardingRuleManager.findById(ruleSource.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSourceManager.create(ruleSource);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public void deleteRuleSource(RuleSourcePO ruleSource) {
        MessageForwardingRulePO ruleSnap = messageForwardingRuleManager.findById(ruleSource.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSourceManager.delete(ruleSource);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public List<MessageSourcePO> findRuleSources(String ruleId) {
        List<RuleSourcePO> ruleSources = ruleSourceManager.findListByRuleId(ruleId);
        if (CollectionUtil.isEmpty(ruleSources)) {
            return Collections.emptyList();
        }
        return messageSourceManager.findListByIds(ruleSources.stream().map(RuleSourcePO::getSourceId).collect(Collectors.toList()));
    }

    public void addRuleSink(RuleSinkPO ruleSink) {
        MessageForwardingRulePO ruleSnap = messageForwardingRuleManager.findById(ruleSink.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSinkManager.create(ruleSink);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public void deleteRuleSink(RuleSinkPO ruleSink) {
        MessageForwardingRulePO ruleSnap = messageForwardingRuleManager.findById(ruleSink.getRuleId()).orElseThrow(() -> new NotFoundException("MESSAGE_FORWARDING_RULE_NOT_FOUND"));
        ruleSinkManager.delete(ruleSink);
        if (BooleanUtil.isTrue(ruleSnap.getEnabled())) {
            applicationEventPublisher.publishEvent(new MessageForwardingRuleChangeEvent(this, ruleSnap));
        }
    }

    public List<MessageSinkPO> findRuleSinks(String ruleId) {
        List<RuleSinkPO> ruleSinks = ruleSinkManager.findListByRuleId(ruleId);
        if (CollectionUtil.isEmpty(ruleSinks)) {
            return Collections.emptyList();
        }
        return messageSinkManager.findListByIds(ruleSinks.stream().map(RuleSinkPO::getSinkId).collect(Collectors.toList()));
    }

    /**
     * 触发转发
     *
     * @param topic
     * @param message
     */
    public void fireForwards(String topic, byte[] message) {
        try {
            forwardingActionFactory.fireForwardRule(topic, message);
        } catch (Exception e) {
            log.error("[MessageForwardingRuleService#fireForwards] fail {}", e.getMessage(), e);
        }
    }

}
