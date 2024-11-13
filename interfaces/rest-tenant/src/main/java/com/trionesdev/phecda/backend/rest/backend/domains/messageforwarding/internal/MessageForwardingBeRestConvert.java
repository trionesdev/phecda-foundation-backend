package com.trionesdev.phecda.backend.rest.backend.domains.messageforwarding.internal;

import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSource;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import com.trionesdev.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("messageForwardingBeRestConvert")
public interface MessageForwardingBeRestConvert {

    MessageForwardingBeRestConvert INSTANCE = Mappers.getMapper(MessageForwardingBeRestConvert.class);

    MessageSource from(MessageSourceCreateRO args);

    MessageSource from(MessageSourceUpdateRO args);


    MessageSourceTopic from(MessageSourceTopicRO.Create args);

    MessageSinkPO from(MessageSinkCreateRO args);

    MessageSinkPO from(MessageSinkUpdateRO args);


    MessageForwardingRulePO from(MessageForwardingRuleCreateRO args);

    MessageForwardingRulePO from(MessageForwardingRuleUpdateRO args);

}
