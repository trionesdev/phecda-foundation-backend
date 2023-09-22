package ms.phecda.backend.core.modules.dict.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryCriteria {

    private List<String> typeCodes;
    private String typeCode;

    private String parentCode;
    private String code;

}
