/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.bot.command;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum Command {
    
    ID("id"),
    HELPME("helpme"),
    IP("ip:");
    
    private final String name;

    private Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
