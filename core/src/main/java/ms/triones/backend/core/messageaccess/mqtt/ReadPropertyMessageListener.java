package ms.triones.backend.core.messageaccess.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.messageaccess.event.DevicePropertyPostEvent;
import ms.triones.backend.core.messageaccess.model.ReadPropertyMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class ReadPropertyMessageListener implements IMqttMessageListener {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    private LinkedBlockingQueue<ReadPropertyMessage> messageQueue = Queues.newLinkedBlockingQueue(30000);
    private Thread thread;
    private AtomicBoolean stopFlag = new AtomicBoolean(false);

    @PostConstruct
    public void init() {
        thread = new Thread(() -> {
            while ((!stopFlag.get()) && (!Thread.interrupted())) {
                try {
                    ReadPropertyMessage message = messageQueue.take();
                    if (Objects.nonNull(message)) {
                        applicationEventPublisher.publishEvent(DevicePropertyPostEvent.build(message));
                    }
                } catch (Exception e) {
                    log.error("handle ReadPropertyMessage error: ", e);
                }
            }
        });
        thread.setName("t-readPropertyMessage");
        thread.start();
    }

    @PreDestroy
    public void destroy() {
        stopFlag.set(true);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("receice message, topic: {}, message: {}", topic, message);
        byte[] payload = message.getPayload();
        ReadPropertyMessage phecdaMessage = JSONObject.parseObject(new String(payload), ReadPropertyMessage.class);
        if (Objects.isNull(phecdaMessage) || CollectionUtils.isEmpty(phecdaMessage.getReadings())) {
            return;
        }

        boolean res = messageQueue.offer(phecdaMessage);
        if (!res) {
            log.warn("can not offer message to queue, message will discard.");
        }
    }
}
