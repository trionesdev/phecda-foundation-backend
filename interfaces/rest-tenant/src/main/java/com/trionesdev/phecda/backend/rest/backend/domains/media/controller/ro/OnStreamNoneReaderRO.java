package com.trionesdev.phecda.backend.rest.backend.domains.media.controller.ro;

import lombok.Data;

@Data
public class OnStreamNoneReaderRO {
    private String app;
    private String schema;
    private String stream;
    private String vhost;
    private String mediaServerId;
}
