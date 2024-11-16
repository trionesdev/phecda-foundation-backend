package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model;

import lombok.Data;
import org.jeasy.rules.api.Facts;

@Data
public class MessageForwardingCmd {
    private Facts facts;
    private byte[] content;
}
