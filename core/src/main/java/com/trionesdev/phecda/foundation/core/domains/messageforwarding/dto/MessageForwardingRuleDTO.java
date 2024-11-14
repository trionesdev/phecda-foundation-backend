package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageForwardingRuleDTO {
    private String id;
    private String name;
    private String description;
    private Boolean enabled;
    private MessageSourceDTO source;
    private Collection<MessageSinkPO> sinks;
}
