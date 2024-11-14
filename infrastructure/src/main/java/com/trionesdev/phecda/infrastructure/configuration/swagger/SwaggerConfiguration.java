package com.trionesdev.phecda.infrastructure.configuration.swagger;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi backendApi() {
        return GroupedOpenApi.builder()
                .group("PhecdaTenantAPI")
                .pathsToMatch("/tenant-api/**")
                .packagesToScan("com.trionesdev.phecda.foundation.rest.tenant")
                .addOpenApiCustomizer(authorizationOpenApiCustomiser())
                .build();
    }

    @Bean
    public GroupedOpenApi sspApi() {
        return GroupedOpenApi.builder()
                .group("Phecda SSP API")
                .pathsToMatch("/ssp-api/**")
                .packagesToScan("com.trionesdev.phecda.foundation.rest.ssp")
                .addOpenApiCustomizer(authorizationOpenApiCustomiser())
                .build();
    }

    public OpenApiCustomizer authorizationOpenApiCustomiser() {
        return openApi -> openApi.schemaRequirement("AUTHORIZATION", new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name(AUTHORIZATION))
                .addSecurityItem(new SecurityRequirement().addList("AUTHORIZATION"))
                ;
    }

}
