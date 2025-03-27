package com.example.eventms.organizer.config;

import com.example.eventms.common.config.BaseOpenAPIConfig;
import com.example.eventms.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig extends BaseOpenAPIConfig {
    @Override
    public SwaggerProperties getSwaggerProperties() {
        return SwaggerProperties.builder()
                .title("REST API - Eventms Organizer")
                .description("Some custom description of API.")
                .contactName("vicendy04")
                .contactEmail("support@example.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
