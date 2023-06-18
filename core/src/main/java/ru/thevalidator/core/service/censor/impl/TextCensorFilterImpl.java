/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.service.censor.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.thevalidator.core.service.censor.TextCensorFilterService;
import ru.thevalidator.core.util.ExceptionUtil;

@Service
public class TextCensorFilterImpl implements TextCensorFilterService {

    private static final Logger logger = LogManager.getLogger(TextCensorFilterImpl.class);
    private Set<String> swearWordsDictionary;
    
    public TextCensorFilterImpl(@Value("${words.dict.path}") String path) {
        this.swearWordsDictionary = new HashSet<>();
        try (Reader reader = new FileReader(path,
                Charset.forName("UTF-8")); BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                swearWordsDictionary.add(line);
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
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

}
