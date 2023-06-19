/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.thevalidator.core.entity.SwearWord;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Repository
public interface SwearWordsRepository extends JpaRepository<SwearWord, Integer> {

    @Query(value = ""
            + "SELECT text_value "
            + "FROM public.word "
            + "WHERE category >= (:category);"
            , nativeQuery = true)
    List<String> findAllWordsByCategory(@Param("category") int category);

    @Query(value = ""
            + "SELECT category "
            + "FROM public.category "
            + "WHERE id=1;"
            , nativeQuery = true)
    Integer findFilterCategory();
    
    @Modifying
    @Query(value = "UPDATE public.category "
            + "SET category=(:category) "
            + "WHERE id=1;"
            , nativeQuery = true)
    void saveFilterCategory(@Param("category") int category);
    
}
