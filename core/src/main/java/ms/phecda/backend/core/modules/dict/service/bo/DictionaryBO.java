package ms.phecda.backend.core.modules.dict.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
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
@SuperBuilder
public class DictionaryBO {

    private String id;

    private String typeCode;

    private String label;

    private String code;

    private String parentCode;

    private String sort;

    private String remark;

    private List<DictionaryBO> children;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

}