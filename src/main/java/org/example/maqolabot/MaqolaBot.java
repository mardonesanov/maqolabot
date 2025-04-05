package org.example.maqolabot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.maqolabot.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaqolaBot extends TelegramLongPollingBot {
    private static final String APP_URL = "https://prominent-brigitta-app-muslim-748ab13e.koyeb.app/";

    private final BotConfig botConfig;

    @PostConstruct
    public void init() {
        // Set the menu button to open the web app (optional, if you still want this functionality)
        try {
            SetChatMenuButton setChatMenuButton = new SetChatMenuButton();
            MenuButtonWebApp menuButtonWebApp = MenuButtonWebApp.builder()
                    .text("🌐 Ochish")
                    .webAppInfo(new WebAppInfo(APP_URL))
                    .build();
            setChatMenuButton.setMenuButton(menuButtonWebApp);
            execute(setChatMenuButton);
            log.info("Menu button set to web app successfully");
        } catch (TelegramApiException e) {
            log.error("Failed to set menu button: {}", e.getMessage(), e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                handleTextMessage(update);
            }
        } catch (Exception e) {
            log.error("Error processing update: {}", e.getMessage(), e);
        }
    }

    private void handleTextMessage(Update update) throws TelegramApiException {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        // Check if the message is "/start"
        if ("/start".equals(messageText)) {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("👋 Assalomu alaykum!\n" +
                    "🎉 MaqolaBotimiz hozirda beta versiyada!\n" +
                    "🚀 Keyinchalik full versiya ishlab chiqariladi.\n" +
                    "📝 Admin bilan bog'lanish uchun quyidagi tugmani bosing! 👇");

            // Create InlineKeyboardMarkup for Admin contact button
            InlineKeyboardMarkup markup = createInlineKeyboard();
            message.setReplyMarkup(markup);

            execute(message);
        }
    }

    private InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Admin button
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton adminButton = new InlineKeyboardButton();
        adminButton.setText("👑 Admin");
        adminButton.setUrl("https://t.me/ll_mardon");
        row1.add(adminButton);

        rows.add(row1);
        markup.setKeyboard(rows);

        return markup;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
