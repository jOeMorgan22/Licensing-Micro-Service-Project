package com.mocksoftwarelicensingproject.licenseservice.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "licenses")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ids")
    private Long id;

    @Column(name = "uuids")
    private UUID uuid;

    @Column(name = "organization_Names")
    private String organizationName;

    @Column(name = "product_names")
    private String productName;

    @Column(name = "license_types")
    private String licenseType;

    @Column(name = "license_descriptions")
    private String description;

    public License() {
    }

    public License(UUID uuid, String organizationName,
                   String productName, String licenseType, String description) {
        this.uuid = uuid;
        this.organizationName = organizationName;
        this.productName = productName;
        this.licenseType = licenseType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuidId() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }
}

