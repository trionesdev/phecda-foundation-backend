package com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.impl;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.*;
import com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro.MessageForwardingRuleCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro.MessageForwardingRuleUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro.RuleSinkCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro.RuleSourceCreateRO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl.MessageForwardingRuleService;
import com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.internal.MessageForwardingBeRestConvert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.internal.MessageForwardingConstants.MESSAGE_FORWARDING_URI;

@Tag(name = "消息转发规则")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = MESSAGE_FORWARDING_URI)
public class MessageForwardingRuleController {
    private final MessageForwardingRuleService messageForwardingRuleService;

    @Operation(summary = "创建消息转发规则")
    @PostMapping(value = "forwarding-rules")
    public void createRule(@Validated @RequestBody MessageForwardingRuleCreateRO args) {
        MessageForwardingRulePO rule = MessageForwardingBeRestConvert.INSTANCE.from(args);
        messageForwardingRuleService.create(rule);
    }

    @Operation(summary = "根据ID删除消息转发规则")
    @PostMapping(value = "forwarding-rules/{id}/delete")
    public void deleteById(@PathVariable(value = "id") String id) {
        messageForwardingRuleService.deleteById(id);
    }

    @Operation(summary = "根据ID修改消息转发规则")
    @PutMapping(value = "forwarding-rules/{id}")
    public void updateById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody MessageForwardingRuleUpdateRO args) {
        MessageForwardingRulePO rule = MessageForwardingBeRestConvert.INSTANCE.from(args);
        rule.setId(id);
        messageForwardingRuleService.updateById(rule);
    }

    @Operation(summary = "根据ID获取消息转发规则")
    @GetMapping(value = "forwarding-rules/{id}")
    public MessageForwardingRulePO queryRuleById(@PathVariable(value = "id") String id) {
        return messageForwardingRuleService.findById(id).orElse(null);
    }

    @Operation(summary = "获取消息转发规则列表")
    @GetMapping(value = "forwarding-rules/list")
    public List<MessageForwardingRulePO> queryRules() {
        return messageForwardingRuleService.findList();
    }


    @Operation(summary = "启用消息转发规则")
    @PutMapping(value = "forwarding-rules/{id}/enabled")
    public void enable(
            @PathVariable(value = "id") String id
    ) {
        messageForwardingRuleService.changeEnable(id, true);
    }

    @Operation(summary = "禁用消息转发规则")
    @PutMapping(value = "forwarding-rules/{id}/disabled")
    public void disableRule(
            @PathVariable(value = "id") String id
    ) {
        messageForwardingRuleService.changeEnable(id, false);
    }

    @Operation(summary = "添加消息转发规则的源")
    @PostMapping(value = "forwarding-rules/{id}/sources")
    public void addRuleSource(
            @PathVariable(value = "id") String ruleId,
            @Validated @RequestBody RuleSourceCreateRO args
    ) {
        RuleSource ruleSource = RuleSource.builder().ruleId(ruleId).sourceId(args.getSourceId()).build();
        messageForwardingRuleService.addRuleSource(ruleSource);
    }

    @Operation(summary = "删除消息转发规则的源")
    @DeleteMapping(value = "forwarding-rules/{id}/sources/source-id/{sourceId}")
    public void deleteRuleSource(
            @PathVariable(value = "id") String ruleId,
            @PathVariable(value = "sourceId") String sourceId
    ) {
        RuleSource ruleSource = RuleSource.builder().ruleId(ruleId).sourceId(sourceId).build();
        messageForwardingRuleService.deleteRuleSource(ruleSource);
    }

    @Operation(summary = "获取消息转发规则的源")
    @GetMapping(value = "forwarding-rules/{id}/sources/list")
    public List<MessageSource> findRuleSourcesList(@PathVariable(value = "id") String ruleId) {
        return messageForwardingRuleService.findRuleSources(ruleId);
    }

    @Operation(summary = "添加消息转发规则的目标")
    @PostMapping(value = "forwarding-rules/{id}/sinks")
    public void addRuleSink(
            @PathVariable(value = "id") String ruleId,
            @Validated @RequestBody RuleSinkCreateRO args
    ) {
        RuleSink ruleSink = RuleSink.builder().ruleId(ruleId).sinkId(args.getSinkId()).build();
        messageForwardingRuleService.addRuleSink(ruleSink);
    }

    @Operation(summary = "删除消息转发规则的目的")
    @DeleteMapping(value = "forwarding-rules/{id}/sinks/sink-id/{sinkId}")
    public void deleteRuleSink(
            @PathVariable(value = "id") String ruleId,
            @PathVariable(value = "sinkId") String sinkId
    ) {
        RuleSink ruleSink = RuleSink.builder().ruleId(ruleId).sinkId(sinkId).build();
        messageForwardingRuleService.deleteRuleSink(ruleSink);
    }

    @Operation(summary = "获取消息转发规则的目标")
    @GetMapping(value = "forwarding-rules/{id}/sinks/list")
    public List<MessageSinkPO> findRuleSinks(@PathVariable(value = "id") String ruleId) {
        return messageForwardingRuleService.findRuleSinks(ruleId);
    }

}
