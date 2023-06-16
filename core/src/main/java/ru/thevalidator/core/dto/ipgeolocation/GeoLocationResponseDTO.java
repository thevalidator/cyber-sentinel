/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.core.dto.ipgeolocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationResponseDTO {
    @JsonProperty("ip_address")
    private String ipAddress;
    private String city;
    private String region;
    private String country;
    private ConnectionDTO connection;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ConnectionDTO getConnection() {
        return connection;
    }

    public void setConnection(ConnectionDTO connection) {
        this.connection = connection;
    }

    @Override
    public String toString() {
        return "[IP]=" + ipAddress 
                + ",\n[CITY]=" + city 
                + ",\n[REGION]=" + region 
                + ",\n[COUNTRY]=" + country 
                + ",\n[CONNECTION TYPE]=" + connection.getConnectionType()
                + ",\n[ISP]=" + connection.getIspName()
                + ",\n[ORGANIZATION]=" + connection.getOrganizationName()
                + ",\n[AUTONOMOUS SYSTEM ORGANIZATION]=" + connection.getAutonomousSystemOrganization()
                ;
    }

}
