package org.example.maqolabot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    private final String token;
    private final String username;

    public BotConfig(
            @Value("${telegram.bot.token}") String token,
            @Value("${telegram.bot.username}") String username) {
        this.token = validateToken(token);
        this.username = validateUsername(username);
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    private String validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Telegram bot token cannot be null or empty");
        }
        if (!token.matches("\\d+:[-_A-Za-z0-9]+")) {
            throw new IllegalArgumentException("Invalid Telegram bot token format");
        }
        return token;
    }

    private String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Telegram bot username cannot be null or empty");
        }
        if (!username.matches("@?[A-Za-z0-9_]{5,32}")) {
            throw new IllegalArgumentException("Invalid Telegram bot username format");
        }
        return username.startsWith("@") ? username : "@" + username;
    }
}