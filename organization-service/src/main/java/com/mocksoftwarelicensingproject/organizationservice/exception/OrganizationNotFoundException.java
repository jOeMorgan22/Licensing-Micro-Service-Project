package com.mocksoftwarelicensingproject.organizationservice.exception;

import java.util.UUID;

public class OrganizationNotFoundException extends RuntimeException{

    public OrganizationNotFoundException(){
        super("Organization not found");
    }

    public OrganizationNotFoundException(Long organizationId){
        super("Organization " + organizationId + " not found");
    }

    public OrganizationNotFoundException(String organizationName){
        super("Organization '" + organizationName + "' not found");
    }

}
