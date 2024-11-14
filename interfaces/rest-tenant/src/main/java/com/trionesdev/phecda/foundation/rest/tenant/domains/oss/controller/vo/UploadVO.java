package com.trionesdev.phecda.foundation.rest.tenant.domains.oss.controller.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadVO {
    private String uid;
    private String url;
    private String fileName;
}
