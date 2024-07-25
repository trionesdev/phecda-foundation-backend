package ms.phecda.backend.core.domains.messageforwarding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSourceDTO {
    private String id;
    private List<Topic> topics;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Topic {
        private String id;
        private String topic;
    }
}
