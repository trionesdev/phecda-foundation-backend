package ms.phecda.backend.core.provider.ssp.device.pdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class InvokeServiceArgPDO {
    private String productKey;
    private String deviceName;
    private String serviceIdentifier;
    private Map<String, String> params;
    private Map<String, Object> body;
    private Map<String, String> tags;
}
