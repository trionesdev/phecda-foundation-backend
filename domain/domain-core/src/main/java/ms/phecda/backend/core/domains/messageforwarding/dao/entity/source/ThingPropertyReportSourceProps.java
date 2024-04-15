package ms.phecda.backend.core.domains.messageforwarding.dao.entity.source;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.internal.util.TopicUtils;

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

    @Override
    public String generateTopic() {
        String productKey = Objects.nonNull(getProductKey()) ? getProductKey() : "+";
        String deviceName = Objects.nonNull(getDeviceName()) ? getDeviceName() : "+";
        if (Objects.nonNull(topicTemplate)) {
            return topicTemplate.getTemplate().replaceAll("\\$\\{productKey\\}", productKey)
                    .replaceAll("\\$\\{deviceName\\}", deviceName)
                    ;
        }
        return null;
    }

    @Getter
    @AllArgsConstructor
    public enum TopicTemplate {
        THING_PROPERTY_POST(TopicUtils.propertyPostTopic("${productKey}", "${deviceName}"));
        private final String template;
    }
}
