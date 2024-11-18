package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSourceConditionItem {
    private String key;
    private String value;
}
