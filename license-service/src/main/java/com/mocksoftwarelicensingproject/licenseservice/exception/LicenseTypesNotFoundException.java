package com.mocksoftwarelicensingproject.licenseservice.exception;

public class LicenseTypesNotFoundException extends RuntimeException{

    public LicenseTypesNotFoundException(String licenseType){
        super("No license of type: " + licenseType + " were found");
    }
}
