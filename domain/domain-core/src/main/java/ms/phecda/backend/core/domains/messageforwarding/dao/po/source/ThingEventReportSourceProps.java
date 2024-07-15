package ms.phecda.backend.core.domains.messageforwarding.dao.po.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingEventReportSourceProps extends SourceProps {
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


}
