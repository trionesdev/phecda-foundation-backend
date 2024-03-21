package ms.phecda.backend.core.domains.device.service.bo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ProductStatisticsBO {
    private Integer count;
    private Integer publishedCount;
    private Integer unpublishedCount;
}
