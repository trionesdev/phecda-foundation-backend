package ms.phecda.edgex.device;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class SendDeviceCmdRequest {
    private String nodeId;
    private Action action;
    private String deviceName;
    private String commandName;
    private Object commandValue;

    public enum Action {
        GET,
        SET
    }
}
