/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.service.censor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.thevalidator.core.entity.SwearWord;
import ru.thevalidator.core.repository.SwearWordsRepository;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
@SpringBootTest
public class TextCensorFilterServiceTest {
    
    @Autowired
    @Qualifier("textcensorfilterserviceimpl")
    private TextCensorFilterService textService;
    
    public TextCensorFilterServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp(@Autowired SwearWordsRepository repository) {
        repository.deleteAll();
        textService.updateWords();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetFilteredText() {
        System.out.println("getFilteredText");
        String text = "someverybadword";
        String expResult = "word *************** word";
        
        SwearWord word = new SwearWord();
        word.setCategory(1);
        word.setTextValue(text);
        textService.addWordToFilter(word);
        
        String result = textService.getFilteredText("word " + text + " word");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetFilteredTextWithoutSwearWords() {
        System.out.println("getFilteredText");
        String text = "someverybadword";
        String expResult = null;
        
        String result = textService.getFilteredText("word " + text + " word");
        assertEquals(expResult, result);
    }
    
}
