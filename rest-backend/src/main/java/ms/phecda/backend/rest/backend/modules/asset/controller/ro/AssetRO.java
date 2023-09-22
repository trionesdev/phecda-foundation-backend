package ms.phecda.backend.rest.backend.modules.asset.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.modules.asset.dao.entity.Asset.UploadItem;
import ms.phecda.backend.core.modules.asset.dao.entity.enums.AssetStateEnum;

import java.util.List;

@Data
public class AssetRO {
    private String name;
    private String sn;
    private String specification;
    private String typeCode;
    private List<String> deviceNames;
    private String locationCode;
    private AssetStateEnum state;
    private String departmentCode;
    private String postCode;
    private List<UploadItem> images;
    private List<UploadItem> files;
    private String remark;
}
