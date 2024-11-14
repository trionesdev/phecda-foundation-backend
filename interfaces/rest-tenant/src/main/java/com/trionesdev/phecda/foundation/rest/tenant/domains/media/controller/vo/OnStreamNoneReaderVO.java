package com.trionesdev.phecda.foundation.rest.tenant.domains.media.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OnStreamNoneReaderVO {
    private Boolean close;
    private Integer code;
}
