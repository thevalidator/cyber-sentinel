/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.censor.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thevalidator.core.entity.SwearWord;
import ru.thevalidator.core.repository.SwearWordsRepository;
import ru.thevalidator.core.service.censor.SwearWordsService;

@Service
@Transactional
public class SwearWordsServiceImpl implements SwearWordsService {
    
    private final SwearWordsRepository repository;

    @Autowired
    public SwearWordsServiceImpl(SwearWordsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(SwearWord word) {
        repository.saveAndFlush(word);
    }

    @Override
    public void saveAll(List<SwearWord> words) {
        repository.saveAllAndFlush(words);
    }

    @Override
    public SwearWord getById(int id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<String> getAllWordsFilteredByCategory(int category) {
        return repository.findAllWordsByCategory(category);
    }

}
