package com.metinkuzey.cleanapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cleanApiOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Day 01 Clean Architecture API")
                        .description("Spring Boot REST API skeleton with clean architecture boundaries")
                        .version("v1")
                        .license(new License().name("MIT")));
    }
}
