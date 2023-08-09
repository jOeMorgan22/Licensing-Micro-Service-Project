package com.mocksoftwarelicensingproject.licenseservice.service;

import com.mocksoftwarelicensingproject.licenseservice.FallbackResponses.LicenseFallback;
import com.mocksoftwarelicensingproject.licenseservice.FallbackResponses.OrganizationFallback;
import com.mocksoftwarelicensingproject.licenseservice.clients.OrganizationFeignClient;
import com.mocksoftwarelicensingproject.licenseservice.clients.OrganizationRestTemplateClient;
import com.mocksoftwarelicensingproject.licenseservice.exception.*;
import com.mocksoftwarelicensingproject.licenseservice.model.License;
import com.mocksoftwarelicensingproject.licenseservice.model.Organization;
import com.mocksoftwarelicensingproject.licenseservice.model.OrganizationWithLicenses;
import com.mocksoftwarelicensingproject.licenseservice.repository.LicenseRepository;
import com.mocksoftwarelicensingproject.licenseservice.requestobjs.LicenseRequest;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    private final OrganizationRestTemplateClient organizationRestTemplateClient;

    public LicenseService(LicenseRepository licenseRepository, OrganizationRestTemplateClient organizationRestTemplateClient){
        this.licenseRepository = licenseRepository;
        this.organizationRestTemplateClient = organizationRestTemplateClient;
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnFallbackLicense")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnFallbackLicense")
    public License createNewLicense(License license){
        Optional<License> potentialConflict = licenseRepository
                .findLicensesByLicenseTypeAndProductNameAndOrganizationName(
                        license.getLicenseType(),
                        license.getProductName(),
                        license.getOrganizationName());
        if(potentialConflict.isPresent()){
            throw new LicenseAlreadyExistsException();
        }
        return licenseRepository.save(license);
    }

    public UUID generateUniqueUUID(String organizationId, LicenseRequest licenseRequest){
        String licenseDescription = organizationId +
                licenseRequest.getProductName() + licenseRequest.getLicenseType();
        byte[] descriptionInBytes = licenseDescription.getBytes(StandardCharsets.UTF_8);
        return UUID.nameUUIDFromBytes(descriptionInBytes);
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnFallbackLicense")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnFallbackLicense")
    public void deleteLicenseById(String organizationName, Long id){
        Optional<License> licenseToDelete = licenseRepository.findLicenseById(id);
        if(licenseToDelete.isEmpty()){
            throw new LicenseNotFoundException(id);
        } else if (!licenseToDelete.get()
                .getOrganizationName().equals(organizationName)) {
            throw new OrganizationMismatchException(organizationName, id);
        }
        licenseRepository.delete(licenseToDelete.get());
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnFallbackLicense")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnFallbackLicense")
    public License findLicenseById(String organizationName, Long id){
        Optional<License> queriedLicense = licenseRepository.findLicenseById(id);
        if (queriedLicense.isEmpty()){
            throw new LicenseNotFoundException(id);
        }
        else if (!queriedLicense.get().getOrganizationName().equals(organizationName)) {
            throw new OrganizationMismatchException(organizationName, id);
        }
        return queriedLicense.get();
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnListOfFallbackLicenses")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnListOfFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnListOfFallbackLicense")
    public List<License> findLicensesByLicenseType(String licenseType, String organizationName){
        List<License> queriedLicenses = licenseRepository
                .findLicensesByLicenseTypeAndOrganizationName(licenseType, organizationName);
        if (queriedLicenses.isEmpty()){
            throw new LicenseTypesNotFoundException(licenseType);
        }
        return new ArrayList<>(queriedLicenses);
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnListOfFallbackLicenses")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnListOfFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnListOfFallbackLicense")
    public List<License> findLicensesByOrganizationName(String organizationName){
        List<License> queriedLicenses = licenseRepository.findLicensesByOrganizationName(organizationName);
        if (queriedLicenses.isEmpty()){
            throw new LicenseNotFoundException();
        }
        return new ArrayList<>(queriedLicenses);
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnListOfFallbackLicenses")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnListOfFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnListOfFallbackLicense")
    public List<License> findLicensesByProductName(String productName, String organizationName){
        List<License> queriedLicenses = licenseRepository
                .findLicensesByProductNameAndOrganizationName(productName, organizationName);
        if (queriedLicenses.isEmpty()){
            throw new LicenseProductNameException(productName);
        }
        return new ArrayList<>(queriedLicenses);
    }

    public Organization getOrganizationFromClient(String organizationName){
        return organizationRestTemplateClient.getOrganization(organizationName);
    }

    public List<OrganizationWithLicenses> sortLicensesAndRetrieveOrganizations(List<License> licensesToSort){
        Map<String, List<License>> orgNamesWithLicenses = new HashMap<>();
        for(License license : licensesToSort){
            String organizationName = license.getOrganizationName();
            if(!orgNamesWithLicenses.containsKey(organizationName)){
                orgNamesWithLicenses
                        .put(organizationName, new ArrayList<>());
                orgNamesWithLicenses
                        .get(organizationName)
                        .add(license);
            }else {
                orgNamesWithLicenses
                        .get(organizationName)
                        .add(license);
            }
        }
        List<OrganizationWithLicenses> listToReturn = new ArrayList<>();
        for(String name : orgNamesWithLicenses.keySet()){
            listToReturn.add(
                    new OrganizationWithLicenses(getOrganizationFromClient(name),
                            orgNamesWithLicenses.get(name)));
        }
        return listToReturn;
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnListOfFallbackLicenses")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnListOfFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnListOfFallbackLicense")
    public List<OrganizationWithLicenses> getAllLicensesByType(String licenseType){
        List<License> licensesByType = licenseRepository.findLicensesByLicenseType(licenseType);
        if(licensesByType.isEmpty()){
            throw new LicenseNotFoundException();
        }
        return sortLicensesAndRetrieveOrganizations(licensesByType);
    }

    @CircuitBreaker(name = "license-db-query", fallbackMethod = "returnListOfFallbackLicenses")
    @Bulkhead(name = "license-service-db-bulkhead", fallbackMethod = "returnListOfFallbackLicense")
    @Retry(name = "retry-license-service-db", fallbackMethod = "returnListOfFallbackLicense")
    public List<OrganizationWithLicenses> getAllLicensesByProductName(String productName){
        List<License> licensesByProductName = licenseRepository.findLicensesByProductName(productName);
        if(licensesByProductName.isEmpty()){
            throw new LicenseNotFoundException();
        }
        return sortLicensesAndRetrieveOrganizations(licensesByProductName);
    }


    public License returnFallbackLicense(Throwable t){
        return LicenseFallback.FALL_BACK_LICENSE;
    }

    public List<License> returnListOfFallbackLicenses(Throwable t){return List.of(LicenseFallback.FALL_BACK_LICENSE);}

    public Organization returnFallbackOrganization(Throwable t){
        return OrganizationFallback.FALL_BACK_ORGANIZATION;
    }

}
