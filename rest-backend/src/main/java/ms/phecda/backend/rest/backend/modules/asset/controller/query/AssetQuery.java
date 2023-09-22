package ms.phecda.backend.rest.backend.modules.asset.controller.query;

import lombok.Data;
import ms.phecda.backend.core.modules.asset.dao.entity.enums.AssetStateEnum;

@Data
public class AssetQuery {

    private String typeCode;
    private String locationCode;
    private AssetStateEnum state;
}
