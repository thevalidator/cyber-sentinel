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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            SendMessage msg = null;
            if (text.equalsIgnoreCase("юфуфус")) {
                msg = new SendMessage(chatId, "Эй, Политолог, расскажи нам новости!");
            } else if (text.equalsIgnoreCase("чуйбараш")) {
                msg = new SendMessage(chatId, "Чуй, скинь фотку сисек кого-нибудь из своих бывших");
            }

            if (msg != null) {
                try {
                    execute(msg);
                    //execute(emsg);
                } catch (TelegramApiException ex) {
                    logger.error(ex.getMessage());
                }
            }

        }
////        System.out.println(">> " + botName + " - " + update.getMessage().getText());
////        String chatId = String.valueOf(update.getMessage().getChatId());
////        Integer msgId = update.getMessage().getMessageId();
////        //logger.trace("<< " + update.getMessage().getText());
////        logger.trace(update.toString());
////        //SendMessage msg = new SendMessage(chatId, "Sasai");
////        DeleteMessage msg = new DeleteMessage(chatId, msgId);
////        //EditMessageText emsg = new EditMessageText(chatId, msgId, chatId, chatId, botName, Boolean.FALSE, replyMarkup, entities)
//////        EditMessageText emsg = new EditMessageText();
//////        emsg.setChatId(chatId);
//////        emsg.setMessageId(msgId);
//////        emsg.setText("Corrected");
////        try {
////            execute(msg);
////            //execute(emsg);
////        } catch (TelegramApiException ex) {
////            logger.error(ex.getMessage());
////        }
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
