package com.mocksoftwarelicensingproject.licenseservice.exception;

import java.util.UUID;

public class OrganizationMismatchException extends RuntimeException{

    public OrganizationMismatchException(String organizationName, Long id){
        super("License " + id + " not found for " + organizationName);
    }

    public OrganizationMismatchException(String organizationName, UUID licenseId){
        super("License " + licenseId + " not found for " + organizationName);
    }
}
