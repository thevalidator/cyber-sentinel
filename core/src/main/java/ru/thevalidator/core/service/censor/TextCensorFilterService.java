/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.censor;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface TextCensorFilterService {
    
    String getFilteredText(String text);

}
