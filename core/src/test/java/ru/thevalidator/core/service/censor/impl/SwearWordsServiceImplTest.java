/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.service.censor.impl;

import java.util.ArrayList;
import java.util.List;
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
import ru.thevalidator.core.service.censor.SwearWordsService;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
@SpringBootTest
public class SwearWordsServiceImplTest {
    
    @Autowired
    @Qualifier("swearwordsserviceimpl")
    private SwearWordsService service;
    
    public SwearWordsServiceImplTest() {
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
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSave() {
        System.out.println("save");
        SwearWord word = new SwearWord();
        word.setCategory(1);
        word.setTextValue("some_very_bad_word");
        service.save(word);
        SwearWord wordFromDB = service.getById(1);
        
        assertEquals(word.getTextValue(), wordFromDB.getTextValue());
        assertEquals(word.getCategory(), wordFromDB.getCategory());
    }
    
    @Test
    public void testGetAllWordsFilteredByCategory() {
        System.out.println("get all words filtered by category");
        SwearWord word1 = new SwearWord();
        word1.setCategory(1);
        word1.setTextValue("some_very_bad_word_cat_1");
        SwearWord word2 = new SwearWord();
        word2.setCategory(2);
        word2.setTextValue("some_very_bad_word_cat_2");
        SwearWord word3 = new SwearWord();
        word3.setCategory(3);
        word3.setTextValue("some_very_bad_word_cat_3");
        SwearWord word4 = new SwearWord();
        word4.setCategory(4);
        word4.setTextValue("some_very_bad_word_cat_4");
        
        List<SwearWord> words = new ArrayList<>();
        words.add(word1);
        words.add(word2);
        words.add(word3);
        words.add(word4);
        
        service.saveAll(words);
        
        List<String> filtered = service.getAllWordsFilteredByCategory(4);
        assertEquals(1, filtered.size());
        assertEquals("some_very_bad_word_cat_4", filtered.get(0));
        
        filtered = service.getAllWordsFilteredByCategory(3);
        assertEquals(2, filtered.size());
        
        filtered = service.getAllWordsFilteredByCategory(2);
        assertEquals(3, filtered.size());
        
        filtered = service.getAllWordsFilteredByCategory(1);
        assertEquals(4, filtered.size());
    }
    
    @Test
    public void testGetAllWordsFilteredByCategoryWithEmptyDB() {
        System.out.println("get all words filtered by category with empty DB");
        
        List<String> filtered = service.getAllWordsFilteredByCategory(1);
        assertNotNull(filtered);
        assertEquals(0, filtered.size());
        
    }
    
    @Test
    public void testGetFilterCategory() {
        System.out.println("get filter category");
        
        int category = service.getFilterCategory();
        assertEquals(1, category);
    }
    
    @Test
    public void testSetFilterCategory() {
        System.out.println("set filter category");
        
        service.setFilterCategory(1);
        int category = service.getFilterCategory();
        assertEquals(1, category);
    }
    
}
