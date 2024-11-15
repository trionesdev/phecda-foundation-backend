package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSource;
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
public class MessageForwardingRuleAggregate {
    private String id;
    private String name;
    private String description;
    private Boolean enabled;
    private MessageSource source;
    private Collection<MessageSink> sinks;
}
