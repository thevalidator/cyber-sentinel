/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
@SpringBootApplication
//@EnableFeignClients
public class Core extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Core.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Core.class);
    }   
    
}
