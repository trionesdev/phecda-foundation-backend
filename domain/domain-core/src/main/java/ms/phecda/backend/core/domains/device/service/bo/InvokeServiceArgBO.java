package ms.phecda.backend.core.domains.device.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvokeServiceArgBO {

    private String identifier;
    private Map<String, String> params;
    private Map<String, Object> body;
    private Map<String,String> tags;
}

