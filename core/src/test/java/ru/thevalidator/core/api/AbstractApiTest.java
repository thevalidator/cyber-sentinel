/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.thevalidator.core.dto.ipgeolocation.GeoLocationResponseDTO;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class AbstractApiTest {

    @Test
    public void testGeolocation() throws JsonProcessingException {
        String response = "{\n"
                + "    \"ip_address\": \"75.142.95.204\",\n"
                + "    \"city\": \"Stockholm\",\n"
                + "    \"region\": \"Stockholm County\",\n"
                + "    \"country\": \"Sweden\",\n"
                + "    \"connection\": {\n"
                + "        \"autonomous_system_number\": 210644,\n"
                + "        \"autonomous_system_organization\": \"FAKE GROUP Ltd\",\n"
                + "        \"connection_type\": \"Corporate\",\n"
                + "        \"isp_name\": \"FAKE-Sviaz TV\",\n"
                + "        \"organization_name\": \"FAKE-GARANTIA\"\n"
                + "    }\n"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        GeoLocationResponseDTO dto = objectMapper.readValue(response, GeoLocationResponseDTO.class);
        assertEquals("75.142.95.204", dto.getIpAddress());
        assertEquals("Stockholm", dto.getCity());
        assertEquals("Stockholm County", dto.getRegion());
        assertEquals("Sweden", dto.getCountry());
        assertEquals("FAKE-Sviaz TV", dto.getConnection().getIspName());
        assertEquals("FAKE-GARANTIA", dto.getConnection().getOrganizationName());
        assertEquals("Corporate", dto.getConnection().getConnectionType());
        assertEquals("FAKE GROUP Ltd", dto.getConnection().getAutonomousSystemOrganization());

    }
    
    // ERROR JSON
//    {
//        "error": {
//            "message": "A validation error occurred.",
//            "code": "validation_error",
//            "details": {
//                "ip_address": [
//                    "Invalid IP Address."
//                ]
//            }
//        }
//    }

}
