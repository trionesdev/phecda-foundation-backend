package ms.phecda.backend.core.messageaccess.disruptor.propertiespost;

import com.lmax.disruptor.EventFactory;

public class PropertiesPostEventFactory implements EventFactory<PropertiesPostEvent> {
    @Override
    public PropertiesPostEvent newInstance() {
        return new PropertiesPostEvent();
    }
}
