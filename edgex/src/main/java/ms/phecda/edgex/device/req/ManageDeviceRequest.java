package ms.phecda.edgex.device.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ManageDeviceRequest {
    private String nodeId;
    private Action action;
    private Add add;
    private Update update;
    private Remove remove;


    public enum Action {
        ADD,
        UPDATE,
        REMOVE
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Add {
        private String driver;
        private String deviceName;
        private String thingModelVersion;
        private Map<String,Object> protocols;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String driver;
        private String deviceName;
        private Map<String,Object> protocols;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Remove {
        private String deviceName;
    }

}
