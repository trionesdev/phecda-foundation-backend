package ms.triones.backend.rest.backend.modules.asset.controller.query;

import lombok.Data;

/**
* <p>
* 资产配件
* </p>
*
* @author jscoe
* @since 2023-06-30
*/
@Data
public class SparePartQuery {


    private String id;

    private String name;

    private String sn;

    private String assetSn;

    private String isEnable;

    private String remark;

}