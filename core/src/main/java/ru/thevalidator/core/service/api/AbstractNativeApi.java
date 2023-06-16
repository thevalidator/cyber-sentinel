/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.service.api;

import ru.thevalidator.core.dto.ipgeolocation.GeoLocationResponseDTO;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface AbstractNativeApi {
    GeoLocationResponseDTO getIPGeoLocationData(String ipAddress);
}
