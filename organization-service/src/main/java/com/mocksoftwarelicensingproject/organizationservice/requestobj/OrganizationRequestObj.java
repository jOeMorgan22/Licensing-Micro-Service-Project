package com.mocksoftwarelicensingproject.organizationservice.requestobj;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class OrganizationRequestObj {

    @NotEmpty
    @NotBlank
    private String organizationName;

    @NotEmpty
    @NotBlank
    private String contactName;

    @Email
    private String email;

    @NotEmpty
    @NotBlank
    private Long phoneNumber;

    public OrganizationRequestObj(String organizationName, String contactName,
                                  String email, Long phoneNumber) {
        this.organizationName = organizationName;
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
