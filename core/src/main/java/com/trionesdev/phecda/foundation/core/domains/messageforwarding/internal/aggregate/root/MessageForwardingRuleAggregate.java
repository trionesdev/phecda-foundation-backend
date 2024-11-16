package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root;

import com.trionesdev.phecda.infrastructure.rule.PhecdaRule;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSource;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.jeasy.rules.api.Rule;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public String conditionEl() {
        return source.getRules().stream().map(rule -> {
            return "( " + rule.conditionEl() + " )";
        }).collect(Collectors.joining(" && "));
    }

    public Rule rule(ForwardingActionFactory forwardingActionFactory) {
        if (Objects.isNull(source) || CollectionUtils.isEmpty(source.getRules())) {
            return null;
        }
        if (CollectionUtils.isEmpty(sinks)) {
            return null;
        }

        return new PhecdaRule<byte[]>().name(id).when(conditionEl()).then((facts,content)->{
            for (MessageSink sink : sinks) {
                sink.getAction().setId(sink.getId());
                forwardingActionFactory.write(sink.getAction(),content);
                break;
            }
        });
    }

}
