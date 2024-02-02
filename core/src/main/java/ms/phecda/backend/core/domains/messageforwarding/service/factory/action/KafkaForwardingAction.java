package ms.phecda.backend.core.domains.messageforwarding.service.factory.action;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction.SinkAction;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction.KafkaSinkAction;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.MessageSinkManager;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@ForwardingActionComponent
public class KafkaForwardingAction extends AbsForwardingAction {
    private final MessageSinkManager messageSinkManager;
    private final Map<String, KafkaTemplate<String, String>> kafkaTemplateMap = new HashMap<>();

    public void put(String key, KafkaTemplate<String, String> kafkaTemplate) {
        kafkaTemplateMap.put(key, kafkaTemplate);
    }

    public KafkaTemplate<String, String> get(String key) {
        return kafkaTemplateMap.get(key);
    }


    @PostConstruct
    public void init() {
        kafkaSync();
    }

    public void kafkaSync() {
        List<MessageSink> sinks = messageSinkManager.findOnlineByType(SinkAction.TypeEnum.KAFKA);
        if (CollectionUtil.isNotEmpty(sinks)) {
            sinks.forEach(t -> {
                t.getAction().setId(t.getId());
                try {
                    kafkaTemplateMap.put(t.getId(), createTemplate((KafkaSinkAction) t.getAction()));
                } catch (Exception ex) {
                    log.error("[KafkaForwardingAction] kafka create fail action id: {}", t.getId(), ex);
                }
            });
        }
    }


    public KafkaTemplate<String, String> createTemplate(KafkaSinkAction sinkAction) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, sinkAction.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }

    @Override
    public void write(SinkAction sinkAction, byte[] data) {
        KafkaSinkAction action = (KafkaSinkAction) sinkAction;
        KafkaTemplate<String, String> kafkaTemplate = kafkaTemplateMap.get(action.getId());
        if (Objects.isNull(kafkaTemplate)) {
            log.error("[KafkaForwardingAction] kafka instance not found ,sink id :{}", action.getId());
        }
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(action.getTopic(), new String(data));
        future.addCallback(result -> log.info("success send kafka message: {}", result),
                result -> log.error("fail send kafka message: ", result));
    }
}
