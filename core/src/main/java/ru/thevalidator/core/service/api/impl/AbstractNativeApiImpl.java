/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.thevalidator.core.dto.ipgeolocation.GeoLocationResponseDTO;
import ru.thevalidator.core.service.api.AbstractNativeApi;
import ru.thevalidator.core.util.ExceptionUtil;

@Service
public class AbstractNativeApiImpl implements AbstractNativeApi {
    
    private static final Logger LOGGER = LogManager.getLogger(AbstractNativeApiImpl.class);
    
    @Value("${abstractapi.key}")
    private String apiKey;
    private final ObjectMapper objectMapper;

    @Autowired
    public AbstractNativeApiImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public GeoLocationResponseDTO getIPGeoLocationData(String ipAddress) {
        GeoLocationResponseDTO dto = null;
        try {
            String response = makeGeolocationRequest(ipAddress);
            dto = objectMapper.readValue(response, GeoLocationResponseDTO.class);
        } catch (IOException ex) {
            LOGGER.error(ExceptionUtil.getFormattedDescription(ex));
        }
        return dto;
    }

    private String makeGeolocationRequest(String ipAddress) throws IOException {
        HttpGet request = new HttpGet("https://ipgeolocation.abstractapi.com"
                + "/v1/?api_key=" + apiKey
                + "&ip_address=" + ipAddress
                + "&fields=ip_address,country,region,city,connection");
        CloseableHttpClient client = HttpClients.createDefault();

        return client.execute(request, new BasicHttpClientResponseHandler());
    }

}
