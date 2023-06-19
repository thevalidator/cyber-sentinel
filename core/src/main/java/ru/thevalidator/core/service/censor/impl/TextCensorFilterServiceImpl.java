/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.service.censor.impl;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.thevalidator.core.entity.SwearWord;
import ru.thevalidator.core.service.censor.SwearWordsService;
import ru.thevalidator.core.service.censor.TextCensorFilterService;

@Service
@Qualifier("textcensorfilterserviceimpl")
public class TextCensorFilterServiceImpl implements TextCensorFilterService {

    private static final Logger LOGGER = LogManager.getLogger(TextCensorFilterServiceImpl.class);
    private final SwearWordsService swearWordService;
    private final Set<String> swearWordsDictionary;

    @Autowired
    public TextCensorFilterServiceImpl(SwearWordsService swearWordService) {
        this.swearWordService = swearWordService;
        this.swearWordsDictionary = new HashSet<>();
    }

    @Override
    public String getFilteredText(String text) {
        StringBuilder censoredText = new StringBuilder(text.length());
        StringBuilder word = new StringBuilder();

        for (char c: text.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                word.append(c);
            } else {
                String checked = checkAndFilterWord(word.toString());
                censoredText.append(checked);
                censoredText.append(c);
                word.setLength(0);
            }
        }

        if (!word.isEmpty()) {
            censoredText.append(checkAndFilterWord(word.toString()));
        }

        String censored = censoredText.toString();
        return censored.equals(text) ? null : censored;
    }
    
    @Override
    public void updateWords() {
        swearWordsDictionary.clear();
        postConstruct();
    }

    @Override
    public void addWordToFilter(SwearWord word) {
        swearWordService.save(word);
        updateWords();
    }

    @Override
    public void setFilterCategory(int category) {
        swearWordService.setFilterCategory(category);
        updateWords();
    }

    @Override
    public Integer getFilterCategory() {
        return swearWordService.getFilterCategory();
    }

    private String checkAndFilterWord(String word) {
        if (swearWordsDictionary.contains(word.toLowerCase())) {
            StringBuilder sb = new StringBuilder(word.length());
            for (int i = 0; i < word.length(); i++) {
                sb.append("*");
            }
            return sb.toString();
        }
        return word;
    }
    
    @PostConstruct
    private void postConstruct() {
        int category  = swearWordService.getFilterCategory();
        var words = swearWordService.getAllWordsFilteredByCategory(category);
        swearWordsDictionary.addAll(words);
    }

}
