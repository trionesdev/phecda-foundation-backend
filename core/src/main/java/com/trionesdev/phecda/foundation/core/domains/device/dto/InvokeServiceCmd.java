package com.trionesdev.phecda.foundation.core.domains.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvokeServiceCmd {

    private String identifier;
    private Map<String, String> params;
    private Map<String, Object> body;
    private Map<String,String> tags;
}

