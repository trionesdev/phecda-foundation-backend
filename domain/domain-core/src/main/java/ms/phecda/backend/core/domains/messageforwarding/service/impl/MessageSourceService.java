package ms.phecda.backend.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Sets;
import com.trionesdev.commons.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSource;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSourceTopic;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.RuleSource;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.source.ThingPropertyReportSourceProps;
import ms.phecda.backend.core.domains.messageforwarding.internal.MessageForwardingBeanConvert;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.MessageSourceManager;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.MessageSourceTopicManager;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.RuleSourceManager;
import ms.phecda.backend.core.dto.dervice.ProductDTO;
import ms.phecda.backend.core.dto.messageforwarding.MessageSourceTopicDTO;
import ms.phecda.backend.core.provider.ssp.device.impl.DeviceProvider;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageSourceService {
    private final MessageForwardingBeanConvert convert;
    private final MessageSourceManager messageSourceManager;
    private final MessageSourceTopicManager messageSourceTopicManager;
    private final RuleSourceManager ruleSourceManager;
    private final DeviceProvider deviceProvider;

    public void create(MessageSource record) {
        messageSourceManager.create(record);
    }

    public void deleteById(String id) {
        List<RuleSource> ruleSources = ruleSourceManager.findListBySourceId(id);
        if (CollectionUtil.isNotEmpty(ruleSources)) {
            throw new BusinessException("MESSAGE_SOURCE_USED");
        }
        messageSourceManager.deleteById(id);
    }

    public void updateById(MessageSource record) {
        messageSourceManager.updateById(record);
    }

    public MessageSource findById(String id) {
        return messageSourceManager.findById(id);
    }

    public List<MessageSource> findAll() {
        return messageSourceManager.findAll();
    }

    public void createSourceTopic(MessageSourceTopic sourceTopic) {
        messageSourceTopicManager.create(sourceTopic);
    }

    public List<MessageSourceTopicDTO> findSourceTopics(String sourceId) {
        List<MessageSourceTopic> topics = messageSourceTopicManager.findSourceTopics(sourceId);
        return assembleTopics(topics);
    }

    public void deleteSourceTopic(String sourceId, String topicId) {
        messageSourceTopicManager.deleteById(topicId);
    }

    private List<MessageSourceTopicDTO> assembleTopics(List<MessageSourceTopic> topics) {
        if (CollectionUtil.isEmpty(topics)) {
            return Collections.emptyList();
        }
        Set<String> productKeys = Sets.newHashSet();
        for (MessageSourceTopic topic : topics) {
            if (topic.getProperties() instanceof ThingPropertyReportSourceProps) {
                productKeys.add(((ThingPropertyReportSourceProps) topic.getProperties()).getProductKey());
            }
        }
        Map<String, ProductDTO> productsMap = deviceProvider.findProductsByKeys(productKeys).stream().collect(Collectors.toMap(ProductDTO::getKey, product -> product, (k1, k2) -> k1));
        return topics.stream().map(topic -> {
            var dto = convert.dtoFromPo(topic);

            if (topic.getProperties() instanceof ThingPropertyReportSourceProps props) {
                dto.setProduct(Optional.ofNullable(productsMap.get(props.getProductKey())).map(product -> MessageSourceTopicDTO.Product.builder().productKey(product.getKey()).name(product.getName()).build()).orElse(null));
            }
            return dto;
        }).collect(Collectors.toList());

    }

}
