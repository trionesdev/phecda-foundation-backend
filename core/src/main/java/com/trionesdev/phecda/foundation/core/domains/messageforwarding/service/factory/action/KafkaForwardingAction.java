package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action;

import cn.hutool.core.collection.CollectionUtil;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.enums.SinkActionType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.sinkaction.SinkActionProps;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.sinkaction.KafkaSinkActionProps;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageSinkManager;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@ForwardingActionComponent
public class KafkaForwardingAction extends AbsForwardingAction {
    private final MessageSinkManager messageSinkManager;
    private final Map<String, KafkaTemplate<String, Object>> kafkaTemplateMap = new HashMap<>();

    public void put(String key, KafkaTemplate<String, Object> kafkaTemplate) {
        kafkaTemplateMap.put(key, kafkaTemplate);
    }

    public KafkaTemplate<String, Object> get(String key) {
        return kafkaTemplateMap.get(key);
    }


    @PostConstruct
    public void init() {
        kafkaSync();
    }

    /**
     * 查询所有的kafka的落库信息，并进行初始化实例
     */
    public void kafkaSync() {
        List<MessageSinkPO> sinks = messageSinkManager.findOnlineByType(SinkActionType.KAFKA);
        if (CollectionUtil.isNotEmpty(sinks)) {
            sinks.forEach(t -> {
                t.getAction().setId(t.getId());
                try {
                    kafkaTemplateMap.put(t.getId(), createTemplate((KafkaSinkActionProps) t.getAction()));
                } catch (Exception ex) {
                    log.error("[KafkaForwardingAction#kafkaSync] kafka create fail action id: {}", t.getId(), ex);
                }
            });
        }
    }


    public KafkaTemplate<String, Object> createTemplate(KafkaSinkActionProps sinkAction) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, sinkAction.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }

    @Override
    public void write(SinkActionProps sinkAction, byte[] data) {
        try {
            KafkaSinkActionProps action = (KafkaSinkActionProps) sinkAction;
            KafkaTemplate<String, Object> kafkaTemplate = kafkaTemplateMap.get(action.getId());
            if (Objects.isNull(kafkaTemplate)) {
                log.error("[KafkaForwardingAction] kafka instance not found ,sink id :{} create once again", action.getId());
                kafkaTemplate = createTemplate(action);
                kafkaTemplateMap.put(action.getId(), kafkaTemplate);
            }
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(action.getTopic(), data);
            future.thenAccept(t -> {
                log.info("[KafkaForwardingAction] kafka send success ,sink id :{}", action.getId());
            }).exceptionally(t -> {
                log.error("[KafkaForwardingAction] kafka send fail ,sink id :{}", action.getId(), t);
                return null;
            });
        } catch (Exception e) {
            log.error("[KafkaForwardingAction] kafka send fail ,sink id :{}", sinkAction.getId(), e);
        }
    }
}
