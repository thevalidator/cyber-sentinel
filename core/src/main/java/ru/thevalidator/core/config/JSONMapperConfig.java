/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Configuration
public class JSONMapperConfig {
    
    @Bean
    public ObjectMapper feignDecoder() {
        return new ObjectMapper();
    }

}
