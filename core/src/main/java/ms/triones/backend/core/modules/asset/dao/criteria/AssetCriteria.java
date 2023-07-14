package ms.triones.backend.core.modules.asset.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.triones.backend.core.modules.asset.dao.entity.enums.AssetStateEnum;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AssetCriteria {

    private String typeCode;
    private String locationCode;
    private AssetStateEnum state;

    private List<String> sns;
}
