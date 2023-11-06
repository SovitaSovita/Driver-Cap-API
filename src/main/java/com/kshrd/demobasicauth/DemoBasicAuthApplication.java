package com.kshrd.demobasicauth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@SecurityScheme(
        name = "bearerAuth",  // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(title = "Sample API", version = "v1")
//        security = @SecurityRequirement(name = "basicAuth") // require all endpoint
)
public class DemoBasicAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoBasicAuthApplication.class, args);
    }

}
