package com.mocksoftwarelicensingproject.organizationservice.exception;

public class OrganizationEmailAlreadyExistsException extends RuntimeException{

    public OrganizationEmailAlreadyExistsException(String organizationEmail){
        super("Organization email '" + organizationEmail + "' already exists");
    }
}
