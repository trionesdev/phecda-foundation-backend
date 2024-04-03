package ms.phecda.backend.core.domains.device.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SendServiceArgBO {
    private String identifier;
    private Map<String, String> params;
    private Map<String, Object> body;
}

