/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.bot.handler.impl;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import static ru.thevalidator.core.bot.command.Command.*;
import ru.thevalidator.core.bot.handler.TextMessageHandler;
import ru.thevalidator.core.service.censor.TextCensorFilterService;

@Component
public class TextMessageHandlerImpl implements TextMessageHandler {

    private final TextCensorFilterService filterService;

    public TextMessageHandlerImpl(TextCensorFilterService filterService) {
        this.filterService = filterService;
    }

    @Override
    public void handleTextMessage(Message message, List<BotApiMethod<? extends Serializable>> response) {

        String text = message.getText();
        String chatId = String.valueOf(message.getChatId());

        SendMessage messageResponse = null;
        if (text.equalsIgnoreCase(ID.getName())) {
            String botResponseText;
            if (message.isReply()) {
                message = message.getReplyToMessage();
            }
            var userId = message.getFrom().getId();
            var userName = message.getFrom().getUserName();
            var userFirstName = message.getFrom().getFirstName();
            var userLastName = message.getFrom().getLastName();
            var userLang = message.getFrom().getLanguageCode();
            botResponseText = String.format("id: %d\nusername: %s\nfirst name: %s\nlast name: %s\nlang: %s",
                    userId, userName, userFirstName, userLastName, userLang);
            messageResponse = new SendMessage(chatId, botResponseText);
        } else if (text.startsWith(IP.getName())) {
            messageResponse = new SendMessage(chatId, "Unsupported yet");
        } else if (text.equalsIgnoreCase("юфуфус")) {
            messageResponse = new SendMessage(chatId, "Эй, Политолог, расскажи нам новости!");
        } else if (text.equalsIgnoreCase("чуйбараш")) {
            messageResponse = new SendMessage(chatId, "Чуй, куда дел черного, падла?");
        } else if (text.equalsIgnoreCase(HELPME.getName())) {
            File file = new File("test.file");
            messageResponse = new SendMessage(chatId, file.getAbsoluteFile().toString());
            //messageResponse = new SendMessage(chatId, "Unsupported yet");
        } else {
            String censorFilteredMessage = filterService.getFilteredText(text);
            if (censorFilteredMessage != null) {
                User user = message.getFrom();
                String description = String.format("<code>Оригинальное сообщение от %s %s содержало бранные слова и было изменено:</code>\n\n",
                        user.getFirstName(),
                        user.getLastName() == null ? "" : user.getLastName());
                messageResponse = new SendMessage(chatId, description + "<i>/\"" + censorFilteredMessage + "\"</i>");
                messageResponse.setParseMode(ParseMode.HTML);
                DeleteMessage deleteMessageResponse = new DeleteMessage(chatId, message.getMessageId());
                response.add(deleteMessageResponse);
            }
        }
        
        if (messageResponse != null) {
            response.add(messageResponse);
        }
        

    }

}
