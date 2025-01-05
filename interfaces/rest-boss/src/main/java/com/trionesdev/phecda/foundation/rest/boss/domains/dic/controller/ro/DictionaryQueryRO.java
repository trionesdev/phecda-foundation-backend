package com.trionesdev.phecda.foundation.rest.boss.domains.dic.controller.ro;

import com.trionesdev.phecda.foundation.core.domains.dic.internal.enums.DictionaryType;
import lombok.Data;

@Data
public class DictionaryQueryRO {
    private DictionaryType type;
    private String typeCode;
}
