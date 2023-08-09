package com.mocksoftwarelicensingproject.organizationservice.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @Column(name = "organization_ids")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_names")
    private String organizationName;

    @Column(name = "contact_names")
    private String contactName;

    @Column(name = "contact_emails")
    private String email;

    @Column(name = "contact_phones")
    private Long phoneNumber;

    public Organization() {
    }

    public Organization(String organizationName, String contactName,
                        String email, Long phoneNumber) {

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

    public void setName(String organizationName) {
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
