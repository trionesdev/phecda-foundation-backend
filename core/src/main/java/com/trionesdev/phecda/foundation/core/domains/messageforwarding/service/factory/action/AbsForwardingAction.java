package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;

public abstract class AbsForwardingAction {

    abstract public void write(SinkAction sinkAction, byte[] data);

}
