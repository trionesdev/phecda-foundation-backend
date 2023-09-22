package ms.phecda.backend.rest.backend.modules.asset.controller.ro;

import lombok.Data;

import java.util.List;

/**
* <p>
* 资产配件
* </p>
*
* @author jscoe
* @since 2023-06-30
*/
@Data
public class SparePartRO {


    private String id;

    private String name;

    private String sn;

    private String assetSn;

    private List<String> deviceNames;

    private Boolean enable;

    private String remark;


}