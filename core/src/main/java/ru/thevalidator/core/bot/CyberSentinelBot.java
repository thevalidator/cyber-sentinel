/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.bot;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.thevalidator.core.service.bot.BotService;
import ru.thevalidator.core.util.ExceptionUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Component
public class CyberSentinelBot extends TelegramLongPollingBot {
    
    private static final Logger logger = LogManager.getLogger(CyberSentinelBot.class);

    @Value("${bot.name}")
    private String botName;
    
    private final BotService botService;
    

    @Autowired
    public CyberSentinelBot(@Value("${bot.token}") String botToken, BotService botService) {
        super(botToken);
        this.botService = botService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        
        var response = botService.handleUpdate(update);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
        
//        if (update.hasMessage() && update.getMessage().hasText()) {
//
//            Message message = update.getMessage();
//
//            String text = message.getText();
//            String chatId = String.valueOf(message.getChatId());
//            
//            SendMessage msg = null;
//            if (text.equalsIgnoreCase("id")) {
//                String botResponseText;
//                if (message.isReply()) {
//                    message = message.getReplyToMessage();
//                }
//                var userId = message.getFrom().getId();
//                var userName = message.getFrom().getUserName();
//                var userFirstName = message.getFrom().getFirstName();
//                var userLastName = message.getFrom().getLastName();
//                var userLang = message.getFrom().getLanguageCode();
//                botResponseText = String.format("id: %d\nusername: %s\nfirst name: %s\nlast name: %s\nlang: %s",
//                        userId, userName, userFirstName, userLastName, userLang);
//                msg = new SendMessage(chatId, botResponseText);
//            } else if (text.equalsIgnoreCase("юфуфус")) {
//                msg = new SendMessage(chatId, "Эй, Политолог, расскажи нам новости!");
//            } else if (text.equalsIgnoreCase("чуйбараш")) {
//                msg = new SendMessage(chatId, "Чуй, куда дел черного, падла?");
//            }
//
//            if (msg != null) {
//                try {
//                    execute(msg);
//                    //execute(emsg);
//                } catch (TelegramApiException ex) {
//                    logger.error(ex.getMessage());
//                }
//            }
//
//        }


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
