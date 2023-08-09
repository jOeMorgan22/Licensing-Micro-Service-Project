package com.mocksoftwarelicensingproject.licenseservice.model;

import java.util.UUID;

public class Organization {

    private Long id;

    private String organizationName;

    private String contactName;

    private String email;

    private Long phoneNumber;

    public Organization() {
    }

    public Organization(Long id, String organizationName, String contactName,
                        String email, Long phoneNumber) {
        this.id = id;
        this.organizationName = organizationName;
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
