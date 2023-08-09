package com.mocksoftwarelicensingproject.licenseservice.FallbackResponses;

import com.mocksoftwarelicensingproject.licenseservice.model.License;

import java.util.UUID;

public class LicenseFallback {

    public static License FALL_BACK_LICENSE = new License(
            UUID.randomUUID(),
            "Unavailable",
            "Unavailable",
            "Unavailable",
            "License retrieval is currently unavailable"
    );

}
