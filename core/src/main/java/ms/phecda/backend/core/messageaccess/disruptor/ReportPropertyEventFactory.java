package ms.phecda.backend.core.messageaccess.disruptor;

import com.lmax.disruptor.EventFactory;

public class ReportPropertyEventFactory implements EventFactory<ReportPropertyEvent> {
    @Override
    public ReportPropertyEvent newInstance() {
        return new ReportPropertyEvent();
    }
}
