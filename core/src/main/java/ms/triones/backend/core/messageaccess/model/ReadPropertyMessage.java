package ms.triones.backend.core.messageaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadPropertyMessage {
    private String messageId;
    private String productId;
    private String deviceName;
    private String thingModelVersion;
    private String identifier;
    private Long timestamp;
    private List<Reading> readings;

    @Data
    public static class Reading {
        private String identifier;
        private Object value;
    }
}
