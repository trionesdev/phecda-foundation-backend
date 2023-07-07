package ms.triones.backend.rest.backend.modules.asset.controller.query;

import lombok.Data;
import ms.triones.backend.core.modules.asset.dao.entity.enums.AssetStateEnum;

@Data
public class AssetQuery {

    private String typeCode;
    private String locationCode;
    private AssetStateEnum state;
}
