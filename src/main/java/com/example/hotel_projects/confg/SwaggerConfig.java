package com.example.hotel_projects.confg;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static org.springframework.security.config.Elements.JWT;

@Configuration
public class SwaggerConfig {
    private static final String BEARER = "Bearer";

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(HTTP)
                .bearerFormat(JWT)
                .scheme(BEARER);
    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Calendar control SERVICE")
                        .description("REST APIS FOR MANAGE Calendar control SERVICE")
                        .version("0.0.1"));
    }
}