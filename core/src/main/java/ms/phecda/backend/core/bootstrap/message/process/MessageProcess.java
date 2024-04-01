package ms.phecda.backend.core.bootstrap.message.process;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.bootstrap.message.disruptor.propertiespost.PropertiesPostEventProducer;
import ms.phecda.backend.core.bootstrap.message.disruptor.propertiespost.PropertiesPostMessage;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import org.springframework.stereotype.Component;

@Deprecated
@Slf4j
@RequiredArgsConstructor
//@Component
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
        propertiesPostEventProducer.sender(topic,postMessage);
        forwardingActionFactory.messageForwarding(topic, JSON.toJSONBytes(postMessage));
    }

}
