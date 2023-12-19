package ms.phecda.backend.core.messageaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadPropertyMessage extends BaseDeviceMessage {
    private List<Reading> params;

    @Data
    public static class Reading {
        private String identifier;
        private String valueType;
        private Object value;
        private Long timestamp;
    }
}
