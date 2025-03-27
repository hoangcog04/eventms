package com.example.eventms.common.config;

import com.example.eventms.common.domain.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public abstract class BaseOpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        SwaggerProperties sp = getSwaggerProperties();
        return new OpenAPI()
                .info(new Info()
                        .title(sp.getTitle())
                        .version(sp.getVersion())
                        .description(sp.getDescription())
                        .contact(new Contact().name(sp.getContactName()).email(sp.getContactEmail())));
    }

    public abstract SwaggerProperties getSwaggerProperties();
}