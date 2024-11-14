package com.trionesdev.phecda.foundation.rest.ssp.sdk.device.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryDeviceReq {
    private String productId;
    private List<String> deviceIds;
}
