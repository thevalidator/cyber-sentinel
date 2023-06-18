/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.bot.handler.impl;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import static ru.thevalidator.core.bot.command.Command.*;
import ru.thevalidator.core.bot.handler.TextMessageHandler;
import ru.thevalidator.core.service.api.RootApi;
import ru.thevalidator.core.service.censor.TextCensorFilterService;

@Component
public class TextMessageHandlerImpl implements TextMessageHandler {

    private final TextCensorFilterService filterService;
    private final RootApi apiService;
    private final String adminId;

    @Autowired
    public TextMessageHandlerImpl(TextCensorFilterService filterService,
            RootApi apiService,
            @Value("${bot.admin.id}") String adminId) {
        this.filterService = filterService;
        this.apiService = apiService;
        this.adminId = adminId;
    }

    @Override
    public void handleTextMessage(Message message, List<BotApiMethod<? extends Serializable>> response) {

        String text = message.getText();
        String chatId = String.valueOf(message.getChatId());

        //System.out.println(">>>> " + text);
        //System.out.println(">>>> " + message.toString());
        SendMessage messageResponse = null;
        if (text.startsWith("/") && String.valueOf(message.getFrom().getId()).equals(adminId)) {
            messageResponse = new SendMessage(chatId, "Unsupported yet");
        } else if (text.equalsIgnoreCase(ID.getName())) {
            String botResponseText;
            if (message.isReply()) {
                message = message.getReplyToMessage();
            }
            User user = message.getFrom();
            User forwardedUser = message.getForwardFrom();
            botResponseText = generateResponseMessageText(user, forwardedUser);
            messageResponse = new SendMessage(chatId, botResponseText);
        }else if (text.startsWith(IP.getName())) {
            String ipAdress = text.substring(3).trim();
            String result = ipAdress.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")
                    ? apiService.getIPGeoLocationData(ipAdress).toString()
                    : "Некорректно введен ip адрес";
            messageResponse = new SendMessage(chatId, result);
        } else if (text.equalsIgnoreCase("юфуфус")) {
            messageResponse = new SendMessage(chatId, "Эй, Политолог, расскажи нам новости!");
        } else if (text.equalsIgnoreCase("чуйбараш")) {
            messageResponse = new SendMessage(chatId, "Чуй, куда дел черного, падла?");
        } else if (text.equalsIgnoreCase(HELPME.getName())) {
            File file = new File("/test.file");
            messageResponse = new SendMessage(chatId, file.getAbsoluteFile().toString());
            //messageResponse = new SendMessage(chatId, "Unsupported yet");
        } else {
            String censorFilteredMessage = filterService.getFilteredText(text);
            if (censorFilteredMessage != null) {
                User user = message.getFrom();
                String description = String.format("<code>Оригинальное сообщение от %s %s содержало бранные слова и было изменено:</code>\n\n",
                        user.getFirstName(),
                        user.getLastName() == null ? "" : user.getLastName());
                messageResponse = new SendMessage(chatId, description + "<i>\"" + censorFilteredMessage + "\"</i>");
                messageResponse.setParseMode(ParseMode.HTML);
                DeleteMessage deleteMessageResponse = new DeleteMessage(chatId, message.getMessageId());
                response.add(deleteMessageResponse);
            }
        }

        if (messageResponse != null) {
            response.add(messageResponse);
        }

    }

    private String generateResponseMessageText(User user, User forwardedUser) {
        String userInfo = getUserInfo(user);
        if (forwardedUser != null) {
            return userInfo + "\n\nFORWARDED FROM:\n" + getUserInfo(forwardedUser);
        } else {
            return userInfo;
        }
    }

    private String getUserInfo(User user) {
        var userId = user.getId();
        var userName = user.getUserName();
        var userFirstName = user.getFirstName();
        var userLastName = user.getLastName();
        var userLang = user.getLanguageCode();
        return String.format("id: %d\nusername: %s\nfirst name: %s\nlast name: %s\nlang: %s",
                    userId, userName, userFirstName, userLastName, userLang);
    }

}
