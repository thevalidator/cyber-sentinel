/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.bot.handler.impl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.thevalidator.core.bot.command.Command;
import static ru.thevalidator.core.bot.command.Command.*;
import ru.thevalidator.core.bot.handler.TextMessageHandler;
import ru.thevalidator.core.entity.SwearWord;
import ru.thevalidator.core.service.api.RootApi;
import ru.thevalidator.core.service.censor.TextCensorFilterService;

@Component
public class TextMessageHandlerImpl implements TextMessageHandler {

    private final TextCensorFilterService filterService;
    private final RootApi apiService;
    private final String adminId;
    private final String groupChatId;

    @Autowired
    public TextMessageHandlerImpl(TextCensorFilterService filterService,
            RootApi apiService,
            @Value("${bot.admin.id}") String adminId,
            @Value("${bot.groupchat.id}") String groupChatId) {
        this.filterService = filterService;
        this.apiService = apiService;
        this.adminId = adminId;
        this.groupChatId = groupChatId;
    }

    @Override
    public void handleTextMessage(Message message, List<BotApiMethod<? extends Serializable>> response) {

        String text = message.getText();
        String chatId = String.valueOf(message.getChatId());

        SendMessage messageResponse = null;
        boolean isAdmin = String.valueOf(message.getFrom().getId()).equals(adminId);
        if (text.startsWith("/")) {
            String botResponseText = null;
            boolean isReply = false;
            if (isAdmin) {
                try {
                    if (text.startsWith(ADD_SWEAR.getName())) {
                        Integer swearCategory = Integer.valueOf(text.substring(5, 6));
                        String swearText = text.substring(7);
                        SwearWord word = new SwearWord(swearText, swearCategory);
                        filterService.addWordToFilter(word);
                        botResponseText = "Новое слово было успешно добавлено";
                    } else if (text.startsWith(SET_CATEGORY.getName())) {
                        Integer swearCategory = Integer.valueOf(text.substring(8, 9));
                        filterService.setFilterCategory(swearCategory);
                        botResponseText = "Установлена " + swearCategory + " категория фильтрации";
                    } else if (text.equals(GET_CATEGORY.getName())) {
                        botResponseText = "Категория фильтрации: " + filterService.getFilterCategory();
                    } else if (text.startsWith(SEND_MESSAGE.getName())) {
                        chatId = groupChatId;
                        response.add(createTypingChatAction(chatId));
                        botResponseText = text.substring(6);
                    } else if (text.equals(HELPME.getName())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Доступные команды:\n");
                        for (Command cmd: Command.values()) {
                            sb.append("-> ").append(cmd.getName()).append("\n");
                        }
                        botResponseText = sb.toString();
                    } else if (text.equals(ID.getName())) {
                        if (message.isReply()) {
                            message = message.getReplyToMessage();
                        }
                        User user = message.getFrom();
                        User forwardedUser = message.getForwardFrom();
                        botResponseText = generateResponseMessageText(user, forwardedUser);
                    } else if (text.startsWith(IP.getName())) {
                        String ipAdress = text.substring(4).trim();
                        botResponseText = ipAdress.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")
                                ? apiService.getIPGeoLocationData(ipAdress).toString()
                                : "Некорректно введен ip адрес";
                    } else {
                        botResponseText = "Unsupported yet";
                    }
                } catch (Exception e) {
                    botResponseText = "Error parsing command, wrong format";
                }
            } else {
                botResponseText = "Сасай, ты не мой создатель";
                isReply = true;
            }
            messageResponse = new SendMessage(chatId, botResponseText);
            if (isReply) {
                messageResponse.setReplyToMessageId(message.getMessageId());
            }
        } else {
            String censorFilteredMessage = filterService.getFilteredText(text);
            if (censorFilteredMessage != null) {
                User user = message.getFrom();
                String description = getDescription(user, censorFilteredMessage);
                messageResponse = new SendMessage(chatId, description);
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

    private SendChatAction createTypingChatAction(String chatId) {
        SendChatAction typingChatAction = new SendChatAction();
        typingChatAction.setChatId(chatId);
        typingChatAction.setAction(ActionType.TYPING);
        return typingChatAction;
    }

    private String getDescription(User user, String censorFilteredMessage) {
        return String.format("<code>Оригинальное сообщение от %s %s "
                + "содержало бранные слова и было изменено:</code>\n\n",
                user.getFirstName(),
                user.getLastName() == null ? "" : user.getLastName())
                + "<i>\"" + censorFilteredMessage + "\"</i>";
    }

}
