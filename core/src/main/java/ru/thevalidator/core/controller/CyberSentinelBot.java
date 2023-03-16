/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Component
public class CyberSentinelBot extends TelegramLongPollingBot {
    
    @Value("${bot.name}")
    private String botName;

    public CyberSentinelBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(">> " + botName + " - " + update.getMessage().getText());
    }

//    @Override
//    public void onUpdatesReceived(List<Update> updates) {
//        super.onUpdatesReceived(updates);
//    }

    @Override
    public String getBotUsername() {
        return botName;
    }

}
