package com.trionesdev.phecda.infrastructure.conf.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.mqtt")
@Data
public class PhecdaMqttProperties {
    private String topicPrefix = "phecda";
}
