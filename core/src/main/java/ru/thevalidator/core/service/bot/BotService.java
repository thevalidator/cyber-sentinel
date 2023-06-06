/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.bot;

import java.io.Serializable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface BotService {
    
    BotApiMethod<? extends Serializable> handleUpdate(Update update);

}
