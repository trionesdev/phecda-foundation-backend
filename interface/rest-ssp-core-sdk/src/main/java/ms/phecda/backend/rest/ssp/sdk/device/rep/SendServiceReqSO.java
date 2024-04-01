package ms.phecda.backend.rest.ssp.sdk.device.rep;

import lombok.Data;

import java.util.Map;

@Data
public class SendServiceReqSO {
    private String identifier;
    private Map<String, String> params;
    private Map<String, Object> body;
}

