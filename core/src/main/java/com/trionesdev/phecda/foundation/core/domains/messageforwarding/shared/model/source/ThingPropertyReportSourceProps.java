package com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.source;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingPropertyReportSourceProps extends SourceProps {
    private String productKey;
    private String deviceName;
    private TopicTemplate topicTemplate;

    public TopicTemplate getTopicTemplate() {
        if (topicTemplate == null) {
            return TopicTemplate.THING_PROPERTY_POST;
        }
        return topicTemplate;
    }

    @Override
    public String generateTopic() {
        String productKey = StringUtils.isNoneBlank(getProductKey()) ? getProductKey() : "+";
        String deviceName = StringUtils.isNoneBlank(getDeviceName()) ? getDeviceName() : "+";
        if (Objects.nonNull(topicTemplate)) {
            return getTopicTemplate().getTemplate().replaceAll("\\$\\{productKey\\}", productKey)
                    .replaceAll("\\$\\{deviceName\\}", deviceName)
                    ;
        }
        return null;
    }


}
