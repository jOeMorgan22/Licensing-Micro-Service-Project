package com.mocksoftwarelicensingproject.licenseservice.model;

import java.util.List;

public class OrganizationWithLicenses {

    private Organization organization;

    private List<License> organizationLicenses;

    public OrganizationWithLicenses(){

    }

    public OrganizationWithLicenses(Organization organization, List<License> organizationLicenses){
        this.organization = organization;
        this.organizationLicenses = organizationLicenses;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<License> getOrganizationLicenses() {
        return organizationLicenses;
    }

    public void setOrganizationLicenses(List<License> organizationLicenses) {
        this.organizationLicenses = organizationLicenses;
    }
}
