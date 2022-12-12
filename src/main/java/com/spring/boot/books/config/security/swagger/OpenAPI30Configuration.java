package com.spring.boot.books.config.security.swagger;


import com.spring.boot.books.config.security.jwt.JwtConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class OpenAPI30Configuration {

  private final JwtConfig jwtConfig;

  @Bean
  public OpenAPI customizeOpenAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement()
            .addList(jwtConfig.getJwtPrefix()))
        .components(new Components()
            .addSecuritySchemes(jwtConfig.getJwtPrefix(),
                new io.swagger.v3.oas.models.security.SecurityScheme()
                    .name(jwtConfig.getJwtPrefix())
                    .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                    .scheme(jwtConfig.getJwtScheme())
                    .bearerFormat(jwtConfig.getJwtFormat())));
  }
}
