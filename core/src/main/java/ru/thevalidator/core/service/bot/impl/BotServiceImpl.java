/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.bot.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.thevalidator.core.bot.handler.TextMessageHandler;
import ru.thevalidator.core.service.bot.BotService;

@Service
public class BotServiceImpl implements BotService {
    
    private final TextMessageHandler textHadler;

    @Autowired
    public BotServiceImpl(TextMessageHandler textHadler) {
        this.textHadler = textHadler;
    }
    

    @Override
    public List<BotApiMethod<? extends Serializable>> getResponse(Update update) {
        
        List<BotApiMethod<? extends Serializable>> response = new ArrayList<>();
        if (hasTextMessage(update) || hasEditedTextMessage(update)) {
            Message message = hasTextMessage(update) ? update.getMessage() : update.getEditedMessage();
            textHadler.handleTextMessage(message, response);
        }
        return response;
    }
    
    private boolean hasTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
    
    private boolean hasEditedTextMessage(Update update) {
        return update.hasEditedMessage() && update.getEditedMessage().hasText();
    }

}
