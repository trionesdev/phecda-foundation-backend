package com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.factory.action;

import com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;

public abstract class AbsForwardingAction {

    abstract public void write(SinkAction sinkAction, byte[] data);

}
