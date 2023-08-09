package com.mocksoftwarelicensingproject.licenseservice.exception;

public class LicenseProductNameException extends RuntimeException{

    public LicenseProductNameException(String productName){
        super("Licenses for " + productName + " not found");
    }
}
