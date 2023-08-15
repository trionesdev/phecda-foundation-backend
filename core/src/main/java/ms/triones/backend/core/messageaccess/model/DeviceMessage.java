package ms.triones.backend.core.messageaccess.model;

import lombok.Data;

import java.util.List;

public class DeviceMessage {
    private String messageId;
    private Long timestamp;

    private String nodeId;
    private String deviceName;
    private String thingModelVersion;
    private String name;
    private Long time;
    private List<Source> sources;

    @Data
    public static class Source {
        private String name;
        private String value;
    }
}
