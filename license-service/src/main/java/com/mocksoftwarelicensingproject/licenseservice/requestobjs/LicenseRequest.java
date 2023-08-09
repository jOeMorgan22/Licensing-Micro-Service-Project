package com.mocksoftwarelicensingproject.licenseservice.requestobjs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LicenseRequest {

    @NotNull
    @NotEmpty
    @NotEmpty
    private String licenseType;

    @NotNull
    @NotEmpty
    @NotEmpty
    private String productName;

    @NotNull
    @NotEmpty
    @NotEmpty
    private String description;

    public LicenseRequest(String licenseType, String productName, String description) {
        this.licenseType = licenseType;
        this.productName = productName;
        this.description = description;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


