package ms.phecda.backend.core.modules.dict.service.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.moensun.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */
@Data
public class DictionaryTypeBO {

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