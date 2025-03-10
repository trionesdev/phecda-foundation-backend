package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal;

import com.trionesdev.commons.exception.TrionesError;

public class MessageForwardingErrors {
    public static final TrionesError MESSAGE_FORWARDING_RULE_NOT_FOUND = TrionesError.builder().code("MESSAGE_FORWARDING_RULE_NOT_FOUND").message("转发规则未找到").build();
}
