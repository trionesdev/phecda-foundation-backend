package com.trionesdev.phecda.foundation.core.messageaccess.model;

import com.trionesdev.phecda.model.device.PhecdaMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceInvokeReplyMessage extends PhecdaMessage {
    private String replyId;
    private String code;
    private String errMsg;
}
