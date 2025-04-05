package org.example.maqolabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class MaqolabotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaqolabotApplication.class, args);
    }

    // Spring Boot will automatically call this method to register the bot
    @Bean
    public TelegramBotsApi telegramBotsApi(MaqolaBot bot) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            System.out.println("Bot successfully registered!");
            return botsApi;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to register Telegram bot", e);
        }
    }
}
