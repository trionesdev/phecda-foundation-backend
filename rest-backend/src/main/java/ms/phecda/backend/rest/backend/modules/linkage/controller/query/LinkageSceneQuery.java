package ms.phecda.backend.rest.backend.modules.linkage.controller.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkageSceneQuery {
    private String name;

    private Integer pageNum;
    private Integer pageSize;
}
