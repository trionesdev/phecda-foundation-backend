package ms.phecda.backend.core.provider.ssp.gatweay.pdo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
public class CommandSendPDO {
    private String version;
    private String id;
    private String method;
    private String productKey;
    private String deviceName;
    private String commandName;
    private Map<String, String> params;
    private Map<String, Object> body;
}
