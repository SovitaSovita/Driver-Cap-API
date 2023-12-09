package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.AlreadyExistException;
import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.AppUser;
import com.kshrd.demobasicauth.model.request.AppUserRequest;
import com.kshrd.demobasicauth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class SimpleTelegramBot extends TelegramLongPollingBot {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;

    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        // Check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();

            // Check if the user typed "/register"
            if ("/register".equalsIgnoreCase(userMessage.trim())) {
                // User wants to register, handle registration logic
                registerUser(update);
            }
//            else {
//                // Handle other commands or messages
//                handleOtherCommands(update);
//            }
        }
    }

    private void registerUser(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        // Assuming your registration logic is in appUserService.register method
        try {
            AppUserRequest appUserRequest = new AppUserRequest();
            appUserRequest.setPassword(update.getMessage().getText());
            appUserRequest.setName(update.getMessage().getFrom().getFirstName() + update.getMessage().getFrom().getLastName());
            appUserRequest.setEmail(update.getMessage().getFrom().getUserName() + "@gmail.com");
            System.out.println(appUserRequest);
            appUserService.register(appUserRequest);

            message.setText("Registration successful! Welcome to our system.");
        }
        catch (AlreadyExistException e){
            message.setText("Registration failed. Email already exists.");
            e.printStackTrace();
        }
        catch (Exception e) {
            message.setText("Registration failed. Please try again later.");
            e.printStackTrace();
        }

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "sovita_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "6751474194:AAGVXE1CmU1Zc3XPdEAW_0_a4vQMzWK5ur0";
    }
}
