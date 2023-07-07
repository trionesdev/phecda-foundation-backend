package ms.triones.backend.rest.backend.modules.dict.controller.ro;

import lombok.Data;

@Data
public class DictionaryRO {
    private String typeCode;
    private String label;
    private String code;
    private String sort;
    private String remark;
}
