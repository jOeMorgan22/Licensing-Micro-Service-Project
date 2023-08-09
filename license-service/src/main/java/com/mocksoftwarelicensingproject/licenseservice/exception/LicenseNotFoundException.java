package com.mocksoftwarelicensingproject.licenseservice.exception;

import java.util.UUID;

public class LicenseNotFoundException extends RuntimeException{

    public LicenseNotFoundException(Long id){
        super("License " + id + " not found");
    }

    public LicenseNotFoundException(UUID licenseId){
        super("License " + licenseId + " not found");
    }

    public LicenseNotFoundException(){
        super("License not found");
    }
}
