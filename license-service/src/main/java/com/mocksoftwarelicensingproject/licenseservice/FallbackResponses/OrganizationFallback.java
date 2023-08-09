package com.mocksoftwarelicensingproject.licenseservice.FallbackResponses;

import com.mocksoftwarelicensingproject.licenseservice.model.Organization;

import java.util.UUID;

public class OrganizationFallback {

    public static Organization FALL_BACK_ORGANIZATION = new Organization(
            0000000000L,
            "Unavailable",
            "Unavailable",
            "Unavailable",
            0000000000L
    );
}
