/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.service.censor;

import java.util.List;
import ru.thevalidator.core.entity.SwearWord;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface SwearWordsService {
    
    SwearWord getById(int id);
    
    List<String> getAllWordsFilteredByCategory(int category);

    void save(SwearWord word);

    void saveAll(List<SwearWord> words);

    int getFilterCategory();
    
    void setFilterCategory(int category);
    
}
