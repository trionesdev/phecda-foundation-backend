package ms.phecda.backend.core.modules.dict.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.moensun.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 字典数据表
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
@TableName(value = "dict_dictionary", autoResultMap = true)
public class Dictionary extends BaseLogicEntity {

    private String id;

    private String typeCode;

    /**
     * 字典标签（例：男/女）
     */
    private String label;

    /**
     * 编码(例：0/1)
     */
    private String code;

    private String parentCode;

    /**
     * 排序
     */
    private String sort;


    /**
     * 备注
     */
    private String remark;


}