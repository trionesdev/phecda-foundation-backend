package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourceTopicPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto.MessageSourceTopicDTO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root.MessageForwardingRuleAggregate;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("messageForwardingBeanConvert")
public interface MessageForwardingDomainConvert {

    MessageSourceTopicDTO dtoFromPo(MessageSourceTopicPO record);

    List<MessageForwardingRuleAggregate> messageForwardingDtoFromEntities(List<MessageForwardingRulePO> records);

    MessageSink messageSinkPoToEntity(MessageSinkPO messageSinkPO);
}
