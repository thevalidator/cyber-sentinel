/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.censor;

import ru.thevalidator.core.entity.SwearWord;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface TextCensorFilterService {
    
    String getFilteredText(String text);
    
    void addWordToFilter(SwearWord word);
    
    void setFilterCategory(int category);

    void updateWords();

    Integer getFilterCategory();

}
