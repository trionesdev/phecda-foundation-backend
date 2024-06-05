package ms.phecda.backend.rest.ssp.sdk.device.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class ServiceInvokeReplyRep {
    private String replyId;
    private String code;
    private String errMsg;

    private String version;
    private String id;
    private String productKey;
    private String productId;
    private String deviceName;
    private String deviceId;
    private String sourceName;
    private Long ts;
    private Map<String, Reading> readings = new HashMap<>();
    private Map<String, Object> tags;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @SuperBuilder
    public static class Reading {
        private Long ts;
        private String identifier;
        private String valueType;
        private String utils;
        private byte[] binaryValue;
        private String mediaType;
        private Object objectValue;
        private String value;

        public Long getTs() {
            return Optional.ofNullable(ts).orElse(Instant.now().toEpochMilli());
        }

        public Object getReadingValue() {
            if (objectValue != null) {
                return objectValue;
            }
            if (value != null) {
                return value;
            }
            return null;
        }
    }
}
