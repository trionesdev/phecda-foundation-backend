package ms.phecda.backend.core.bootstrap.message.process;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import ms.phecda.backend.core.messageaccess.disruptor.propertiespost.PropertiesPostEventProducer;
import ms.phecda.backend.core.messageaccess.disruptor.propertiespost.PropertiesPostMessage;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageProcess {
    private final ForwardingActionFactory forwardingActionFactory;
    private final PropertiesPostEventProducer propertiesPostEventProducer;

    /**
     * process properties post message
     *
     * @param topic
     * @param postMessage
     */
    public void propertiesPost(String topic, PropertiesPostMessage postMessage) {
        propertiesPostEventProducer.sender(postMessage);
        forwardingActionFactory.messageForwarding(topic, JSON.toJSONBytes(postMessage));
    }

}
