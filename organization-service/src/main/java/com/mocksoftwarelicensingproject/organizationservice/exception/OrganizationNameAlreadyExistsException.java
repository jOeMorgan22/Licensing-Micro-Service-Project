package com.mocksoftwarelicensingproject.organizationservice.exception;

public class OrganizationNameAlreadyExistsException extends RuntimeException{

    public OrganizationNameAlreadyExistsException(String organizationName){
        super("Organization name '" + organizationName + "' already exists");
    }
}
