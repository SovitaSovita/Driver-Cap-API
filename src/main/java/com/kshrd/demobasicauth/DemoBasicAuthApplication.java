package com.kshrd.demobasicauth;

import com.kshrd.demobasicauth.service.SimpleTelegramBot;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

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

    private static SimpleTelegramBot simpleTelegramBot;

    public DemoBasicAuthApplication(SimpleTelegramBot simpleTelegramBot) {
        this.simpleTelegramBot = simpleTelegramBot;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoBasicAuthApplication.class, args);

        try {
            // Initialize and register your bot when the application starts
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(simpleTelegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
