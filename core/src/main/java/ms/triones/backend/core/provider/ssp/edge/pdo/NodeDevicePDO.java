package ms.triones.backend.core.provider.ssp.edge.pdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeDevicePDO {
    private String id;
    private String nodeId;
    private String deviceId;
}
