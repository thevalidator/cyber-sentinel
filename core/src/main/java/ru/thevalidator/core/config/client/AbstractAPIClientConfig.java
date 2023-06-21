///*
// * Copyright (C) 2023 thevalidator
// */
//package ru.thevalidator.core.config.client;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import feign.Logger;
//import feign.codec.Decoder;
//import feign.codec.ErrorDecoder;
//import feign.jackson.JacksonDecoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author thevalidator <the.validator@yandex.ru>
// */
//@Configuration
//public class AbstractAPIClientConfig {
//    
//    private static final ObjectMapper objectMapper = new ObjectMapper();
////@Bean
////    public Decoder feignDecoder() {
////        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
////
////        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(jacksonConverter);
////        ObjectFactory<HttpMessageConverters> objectFactory = () -> httpMessageConverters;
////
////
////        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
////    }
////
////    public ObjectMapper customObjectMapper(){
////        ObjectMapper objectMapper = new ObjectMapper();
////        //Customize as much as you want
////        return objectMapper;
////    }
//
////    <dependency>
////      <groupId>io.github.openfeign</groupId>
////      <artifactId>feign-jackson</artifactId>
////      <version>10.1.0</version>
////    </dependency>
//    
//    @Bean
//    public Decoder feignDecoder() {
//        return new JacksonDecoder(objectMapper);
//    }
//    
////    
////    @Bean
////    Logger.Level feignLoggerLevel() {
////        return Logger.Level.FULL;
////    }
////    
////    @Bean
////    public ErrorDecoder errorDecoder() { return new FeignCustomErrorDecoder();}
//}
