package ms.phecda.edgex.device.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ManageDeviceRequest {
    private String nodeId;
    private Action action;
    private Add add;
    private Remove remove;


    public enum Action {
        ADD,
        REMOVE
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Add {
        private String deviceName;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Remove {
        private String deviceName;
    }

}
