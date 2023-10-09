package ms.phecda.backend.rest.backend.domains.dict.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DictionaryRO {
    @NotNull
    private String typeCode;
    private String label;
    @NotNull
    private String code;
    private String sort;
    private String remark;
    private String parentCode;
}
