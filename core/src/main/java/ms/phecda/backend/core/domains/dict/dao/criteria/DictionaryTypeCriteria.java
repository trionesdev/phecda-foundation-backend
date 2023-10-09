package ms.phecda.backend.core.domains.dict.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
* <p>
* 字典类型表
* </p>
*
* @author jscoe
* @since 2023-06-30
*/
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryTypeCriteria {
    private List<String> ids;

    private String id;

    /**
    * 名称
    */
    private String name;

    /**
    * 编号
    */
    private String code;


    /**
    * 备注
    */
    private String remark;


}