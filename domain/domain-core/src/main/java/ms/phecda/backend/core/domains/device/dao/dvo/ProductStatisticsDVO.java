package ms.phecda.backend.core.domains.device.dao.dvo;

import lombok.Data;

@Data
public class ProductStatisticsDVO {
    private Integer count;
    private Integer publishedCount;
    private Integer unpublishedCount;
}
