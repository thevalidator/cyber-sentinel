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
public class ConnectionDTO {
    
    @JsonProperty("connection_type")
    private String connectionType;
    @JsonProperty("autonomous_system_organization")
    private String autonomousSystemOrganization;
    @JsonProperty("isp_name")
    private String ispName;
    @JsonProperty("organization_name")
    private String organizationName;

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getAutonomousSystemOrganization() {
        return autonomousSystemOrganization;
    }

    public void setAutonomousSystemOrganization(String autonomousSystemOrganization) {
        this.autonomousSystemOrganization = autonomousSystemOrganization;
    }

    public String getIspName() {
        return ispName;
    }

    public void setIspName(String ispName) {
        this.ispName = ispName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

}
