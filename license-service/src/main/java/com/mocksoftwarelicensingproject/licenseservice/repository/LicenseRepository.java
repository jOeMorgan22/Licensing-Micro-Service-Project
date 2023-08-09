package com.mocksoftwarelicensingproject.licenseservice.repository;

import com.mocksoftwarelicensingproject.licenseservice.model.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LicenseRepository extends CrudRepository<License, Long> {
    Optional<License> findLicenseById(Long id);
    Optional<License> findLicensesByLicenseTypeAndProductNameAndOrganizationName(String licenseType, String productName, String organizationName);
    List<License> findLicensesByLicenseTypeAndOrganizationName(String licenseType, String organizationName);
    List<License> findLicensesByOrganizationName(String organizationName);
    List<License> findLicensesByProductNameAndOrganizationName(String productName, String organizationName);
    List<License> findLicensesByLicenseType(String licenseType);
    List<License> findLicensesByProductName(String productName);


}
