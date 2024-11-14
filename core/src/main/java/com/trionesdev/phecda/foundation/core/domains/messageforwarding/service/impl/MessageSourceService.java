package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Sets;
import com.trionesdev.commons.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSource;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.RuleSource;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.source.ThingPropertyReportSourceProps;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.MessageForwardingBeanConvert;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageSourceManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageSourceTopicManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.RuleSourceManager;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductDTO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto.MessageSourceTopicDTO;
import com.trionesdev.phecda.foundation.core.domains.device.provider.impl.DeviceProvider;
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
