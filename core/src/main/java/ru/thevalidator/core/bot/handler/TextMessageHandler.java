/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.bot.handler;

import java.io.Serializable;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface TextMessageHandler {
    
    void handleTextMessage(Message message, List<BotApiMethod<? extends Serializable>> response);

}
