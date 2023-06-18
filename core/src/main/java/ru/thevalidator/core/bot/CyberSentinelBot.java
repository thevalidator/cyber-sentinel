/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.bot;

import java.io.Serializable;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
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

        var response = botService.getResponse(update);
        
        if (response != null && !response.isEmpty()) {
            try {
                executeAll(response);
            } catch (TelegramApiException e) {
                logger.error(ExceptionUtil.getFormattedDescription(e));
            }
        }

    }

//    @Override
//    public void onUpdatesReceived(List<Update> updates) {
//        super.onUpdatesReceived(updates);
//    }
    
    @Override
    public String getBotUsername() {
        return botName;
    }

    private void executeAll(List<BotApiMethod<? extends Serializable>> response) throws TelegramApiException {
        for (BotApiMethod<? extends Serializable> m: response) {
            execute(m);
        }
    }

}
