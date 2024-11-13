package com.trionesdev.phecda.backend.rest.backend.domains.media.controller.ro;

import lombok.Data;

@Data
public class OnStreamNotFoundRO {
    private String app;
    private String id;
    private String ip;
    private String params;
    private Integer port;
    private String schema;
    private String stream;
    private String vhost;
    private String mediaServerId;
}
