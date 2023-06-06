/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.bot;

import java.io.Serializable;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface BotService {
    
    List<BotApiMethod<? extends Serializable>> getResponse(Update update);

}
