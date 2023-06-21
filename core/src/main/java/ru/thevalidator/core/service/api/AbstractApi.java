///*
// * Copyright (C) 2023 thevalidator
// */
//
//package ru.thevalidator.core.service.api;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import ru.thevalidator.core.config.client.AbstractAPIClientConfig;
//import ru.thevalidator.core.dto.ipgeolocation.GeoLocationResponseDTO;
//
///**
// * @author thevalidator <the.validator@yandex.ru>
// */
////@FeignClient(value = "AbstractAPI", url = "https://ipgeolocation.abstractapi.com/v1/")
//@FeignClient(value = "AbstractAPI", url = "https://ipgeolocation.abstractapi.com/v1/", configuration = AbstractAPIClientConfig.class)
//public interface AbstractApi {
//    
//    @GetMapping("/?api_key={apiKey}&ip_address={ipAddress}&fields=ip_address,country,region,city,connection")
//    GeoLocationResponseDTO getIPGeoLocationData(@PathVariable("ipAddress") String ipAddress);
//    //UniversalDTO getIPGeoLocationData(@PathVariable("ipAddress") String ipAddress);
//
//}
