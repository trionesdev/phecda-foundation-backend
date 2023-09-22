package ms.phecda.backend.core.modules.asset.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SparePartCriteria {

    private String id;

    private String name;

    private String sn;

    private String assetSn;

    private Boolean enable;

    private String remark;

    private Integer pageNum;
    private Integer pageSize;

    private List<String> sns;
}
