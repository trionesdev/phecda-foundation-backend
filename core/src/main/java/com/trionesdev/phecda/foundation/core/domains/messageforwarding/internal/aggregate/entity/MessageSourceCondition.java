package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.source.MessageSourceConditionItem;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.source.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSourceCondition {
    private String id;
    private String sourceId;
    private MessageType type;
    private List<MessageSourceConditionItem> items;

    public String conditionEl() {
        List<String> rules = new ArrayList<String>();
        if (CollectionUtils.isEmpty(items)) {
            return " true ";
        }
        for (MessageSourceConditionItem item : items) {
            if (StringUtils.isNotBlank(item.getKey()) && StringUtils.isNotBlank(item.getValue())) {
                if (Objects.equals("+", item.getValue().trim())) {
                    rules.add(" true ");
                } else {
                    rules.add(" " + item.getKey() + "=\"" + item.getValue() + "\" ");
                }
            }
        }
        if (CollectionUtils.isEmpty(rules)) {
            return "true";
        }
        return StringUtils.join(rules, " && ");
    }
}
