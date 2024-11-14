package com.trionesdev.phecda.foundation.rest.tenant.domains.media.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OnStreamNotFoundVO {
    private String msg;
    private Integer code;
}
