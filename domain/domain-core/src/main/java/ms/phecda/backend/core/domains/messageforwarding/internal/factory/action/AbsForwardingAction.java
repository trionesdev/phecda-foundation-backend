package ms.phecda.backend.core.domains.messageforwarding.internal.factory.action;

import ms.phecda.backend.core.domains.messageforwarding.dao.po.sinkaction.SinkAction;

public abstract class AbsForwardingAction {

    abstract public void write(SinkAction sinkAction, byte[] data);

}
