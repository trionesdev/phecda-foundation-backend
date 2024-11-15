package com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.internal;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourcePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourceTopicPO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro.*;
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

    MessageSourcePO from(MessageSourceCreateRO args);

    MessageSourcePO from(MessageSourceUpdateRO args);


    MessageSourceTopicPO from(MessageSourceTopicRO.Create args);

    MessageSinkPO from(MessageSinkCreateRO args);

    MessageSinkPO from(MessageSinkUpdateRO args);


    MessageForwardingRulePO from(MessageForwardingRuleCreateRO args);

    MessageForwardingRulePO from(MessageForwardingRuleUpdateRO args);

}
