package com.mocksoftwarelicensingproject.licenseservice.exception;

public class LicenseAlreadyExistsException extends RuntimeException{

    public LicenseAlreadyExistsException(){
        super("License already exists for this organization");
    }
}
