package ms.triones.backend.rest.backend.modules.oss.controller.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadVO {
    private String uid;
    private String url;
}
