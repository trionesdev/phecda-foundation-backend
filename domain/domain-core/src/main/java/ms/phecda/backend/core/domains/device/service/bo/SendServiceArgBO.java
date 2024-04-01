package ms.phecda.backend.core.domains.device.service.bo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
public class SendServiceArgBO {
    private String identifier;
    private Map<String, String> params;
    private Map<String, Object> body;
}

