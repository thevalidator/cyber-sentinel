/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.bot.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.thevalidator.core.bot.handler.TextMessageHandler;
import ru.thevalidator.core.service.bot.BotService;

@Service
public class BotServiceImpl implements BotService {
    
    private final TextMessageHandler textHadler;

    public BotServiceImpl(TextMessageHandler textHadler) {
        this.textHadler = textHadler;
    }
    

    @Override
    public List<BotApiMethod<? extends Serializable>> getResponse(Update update) {
        
        List<BotApiMethod<? extends Serializable>> response = new ArrayList<>();
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            textHadler.handleTextMessage(message, response);
        }
        
        return response;
    }

}
