///*
// * Copyright (C) 2023 thevalidator
// */
//
//package ru.thevalidator.core.config.client;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import feign.Response;
//import feign.codec.ErrorDecoder;
//import java.io.IOException;
//import java.io.Reader;
//import java.nio.charset.StandardCharsets;
//import org.apache.commons.io.IOUtils;
//import ru.thevalidator.core.dto.ipgeolocation.GeoLocationResponseDTO;
//
///**
// * @author thevalidator <the.validator@yandex.ru>
// */
//public class FeignCustomErrorDecoder implements ErrorDecoder {
//
//    @Override
//    public Exception decode(String string, Response response) {
//        Reader reader = null;
//
//        //capturing error message from response body.
//        try {
//            reader = response.body().asReader(StandardCharsets.UTF_8);
//            
//            
//            String result = IOUtils.toString(reader);
//            System.out.println(">>>>> " + result);
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//            GeoLocationResponseDTO geo = mapper.readValue(result,
//                GeoLocationResponseDTO.class);
//
//        } catch (IOException e) {
//            //log.error("IO Exception on reading exception message feign client" + e);
//        }
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//}
