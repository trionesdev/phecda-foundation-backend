package ms.phecda.backend.core.domains.messageforwarding.dao.entity.source;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingPropertyReportSourceProps extends SourceProps {
    private String productId;
    private String deviceName;
    private TopicTemplate topicTemplate;

    @Override
    public String generateTopic() {
        String productId = Objects.nonNull(getProductId()) ? getProductId() : "+";
        String deviceName = Objects.nonNull(getDeviceName()) ? getDeviceName() : "+";
        if (Objects.nonNull(topicTemplate)) {
            return topicTemplate.getTemplate().replaceAll("\\$\\{productId\\}", productId)
                    .replaceAll("\\$\\{deviceName\\}", deviceName)
                    ;
        }
        return null;
    }

    @Getter
    @AllArgsConstructor
    public enum TopicTemplate {
        THING_PROPERTY_POST("phecda/${productId}/${deviceName}/thing/property/post");
        private final String template;
    }
}
