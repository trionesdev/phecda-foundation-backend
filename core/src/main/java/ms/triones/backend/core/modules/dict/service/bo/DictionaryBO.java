package ms.triones.backend.core.modules.dict.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

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
@Builder
public class DictionaryBO {

    private String id;

    private String typeCode;

    private String label;

    private String code;

    private String parentCode;

    private String sort;

    private String remark;

    private List<DictionaryBO> childrenList;

}