package ms.phecda.edge.base.device.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDeviceRequest {
    private String nodeId;

    public String getNodeId() {
        return Objects.isNull(nodeId) ? "default" : nodeId;
    }
}
