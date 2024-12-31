package com.bridge.template.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Auth Service API specifications for MSA",
                description = "Auth Service API specifications with spring boot 3.2 + spring cloud.",
                version ="v0.0.0")
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi customTestOpenAPI() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("SSO 인증 서버")
                .pathsToMatch(paths)
                .build();
    }
}
