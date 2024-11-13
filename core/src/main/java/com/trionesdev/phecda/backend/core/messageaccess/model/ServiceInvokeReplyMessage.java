package com.trionesdev.phecda.backend.core.messageaccess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.trionesdev.phecda.backend.core.internal.disruptor.propertiespost.PropertiesPostMessage;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceInvokeReplyMessage extends PropertiesPostMessage {
    private String replyId;
    private String code;
    private String errMsg;
}
