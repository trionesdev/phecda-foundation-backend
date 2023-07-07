package ms.triones.backend.core.modules.asset.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class SparePartBO {

    /**
    * 主键
    */
    private String id;

    /**
    * 名称
    */
    private String name;

    /**
    * 编号
    */
    private String sn;

    /**
    * 关联资产sn
    */
    private String assetSn;


    private List<String> deviceNames;


    /**
    * 是否启用
    */
    private Boolean enable;

    /**
    * 备注
    */
    private String remark;


}