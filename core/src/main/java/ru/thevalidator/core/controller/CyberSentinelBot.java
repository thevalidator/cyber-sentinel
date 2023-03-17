/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Component
public class CyberSentinelBot extends TelegramLongPollingBot {
    
    @Value("${bot.name}")
    private String botName;
    private static final Logger logger = LogManager.getLogger(CyberSentinelBot.class);

    public CyberSentinelBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(">> " + botName + " - " + update.getMessage().getText());
        String chatId = String.valueOf(update.getMessage().getChatId());
        Integer msgId = update.getMessage().getMessageId();
        //logger.trace("<< " + update.getMessage().getText());
        logger.trace(update.toString());
        //SendMessage msg = new SendMessage(String.valueOf(update.getMessage().getChatId()), "Sasai");
        DeleteMessage msg = new DeleteMessage(chatId, msgId);
        try {
            
            execute(msg);
        } catch (TelegramApiException ex) {
            logger.error(ex.getMessage());
        }
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
