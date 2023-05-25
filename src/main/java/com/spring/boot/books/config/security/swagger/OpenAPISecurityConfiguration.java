package com.spring.boot.books.config.security.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "User API",
        version = "${SpringDoc.api-docs.version}",
        contact = @Contact(
            name = "Allweb", email = "admin@gmail.com", url = "localhost:8080"
        ),
        license = @License(
            name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
        ),
        termsOfService = "${tos.uri}",
        description = "Swagger Info Documentation"
    ),
    servers = @Server(
        url = "${security.jwt.issuer}",
        description = "Development"
    )
)
public class OpenAPISecurityConfiguration {

}
