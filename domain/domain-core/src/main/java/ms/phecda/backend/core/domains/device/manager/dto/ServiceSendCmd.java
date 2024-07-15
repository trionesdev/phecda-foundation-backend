package ms.phecda.backend.core.domains.device.manager.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
public class ServiceSendCmd {
    private String version;
    private String id;
    private Boolean sync;
    private String method;
    private String productKey;
    private String deviceName;
    private String commandName;
    private Map<String, String> params;
    private Map<String, Object> body;
}
